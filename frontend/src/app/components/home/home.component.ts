import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {User} from "../../interfaces/user";
import {SelectedUserService} from "../../services/selected-user.service";
import {CommonModule} from "@angular/common";
import {ReplaceNullWithTextPipe} from "../../pipes/replace-null-with-text.pipe";
import {StudentService} from "../../services/student.service";
import {Course} from "../../interfaces/course";
import {Student} from "../../interfaces/student";
import {Teacher} from "../../interfaces/teacher";
import {CourseService} from "../../services/course.service";
import {forkJoin, map, switchMap} from "rxjs";
import {EnrollmentService} from "../../services/enrollment.service";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ReplaceNullWithTextPipe],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  selectedUser: User | null = null;
  courses: Course[] = [];
  mandatoryCourses: Course[] = [];
  electiveCourses: Course[] = [];
  courseApplicationCounts: { [courseId: number]: number } = {};
  constructor(
    private selectedUserService: SelectedUserService,
    private studentService: StudentService,
    private cdr: ChangeDetectorRef,
    private courseService: CourseService,
    private enrollmentService: EnrollmentService) {}

  ngOnInit(): void {
    this.selectedUserService.selectedUser$.subscribe(user => {
      this.selectedUser = user;
      if (this.selectedUser && ((this.selectedUser as Student).role === 'student')) {
        // this.loadCoursesForStudent(this.selectedUser.id);
        this.loadCoursesSameYearForStudent((this.selectedUser as Student).studyYear)
      }
      if (this.selectedUser && ((this.selectedUser as Teacher).role === 'teacher')) {
        this.courses = (this.selectedUser as Teacher).courses
      }
    });
    this.cdr.detectChanges();
  }

  private loadCoursesSameYearForStudent(studyYear: number): void {
    this.courseService.getCoursesByStudyYear(studyYear).pipe(
      switchMap(courses => {
        this.courses = courses;
        this.mandatoryCourses = this.courses.filter(course => course.category === 'mandatory');
        this.electiveCourses = this.courses.filter(course => course.category === 'elective');

        const applicationObservables = this.courses.map(course =>
          this.enrollmentService.getNrOfApplicationsForCourse(course.id).pipe(
            map(count => ({ courseId: course.id, count }))
          )
        );

        return forkJoin(applicationObservables);
      })
    ).subscribe(applicationCounts => {
      applicationCounts.forEach(appCount => {
        this.courseApplicationCounts[appCount.courseId] = appCount.count;
      });
      console.log(this.courseApplicationCounts);
    });
  }

  isStudent(user: User | null): user is Student {
    return user?.role === 'student';
  }

}
