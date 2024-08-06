import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { EnrollmentAdministrationService } from '../../services/enrollment-administration.service';
import { EnrollmentAdministration } from '../../interfaces/enrollment-administration';
import {Course} from "../../interfaces/course";
import {SelectedUserService} from "../../services/selected-user.service";
import {Student} from "../../interfaces/student";
import {User} from "../../interfaces/user";
import {forkJoin, map, switchMap} from "rxjs";
import {CdkDragDrop, moveItemInArray, DragDropModule, CdkDropList, CdkDrag} from '@angular/cdk/drag-drop';
import {CourseService} from "../../services/course.service";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-elective',
  standalone: true,
  templateUrl: './elective.component.html',
  imports: [
    NgForOf,
    NgIf,
    CdkDropList,
    CdkDrag
  ],
  styleUrls: ['./elective.component.scss']
})
export class ElectiveComponent implements OnInit {

  enrollmentAdministrations: EnrollmentAdministration[] = [];
  electiveCourses: Course[] = [];
  startTime: Date | null = null;
  endTime: Date | null = null;
  protected selectedUser: User | null = null;
  private courses: Course[] = [];
  courseApplicationCounts: { [courseId: number]: number } = {};


  constructor(
    private enrollmentAdministrationService: EnrollmentAdministrationService,
    private selectedUserService: SelectedUserService,
    private courseService: CourseService,
    private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    // Hardcoded values for setting the enrollment period
    const hardcodedStartTime = '2024-08-01';
    const hardcodedEndTime = '2024-08-15';

    // Set the enrollment period with the hardcoded values
    this.setEnrollmentPeriod(hardcodedStartTime, hardcodedEndTime);

    this.enrollmentAdministrationService.getAllEnrollmentAdministration().subscribe({
      next: (enrollments: EnrollmentAdministration[]) => {
        // console.log('Enrollments:', enrollments);
        this.enrollmentAdministrations = enrollments;
      },
      error: (err) => {
        console.error('Error fetching enrollments:', err);
      }
    });

    this.enrollmentAdministrationService.getEnrollmentPeriod().subscribe({
      next: (enrollmentPeriod: string[]) => {
        if (enrollmentPeriod.length === 2) {
          this.startTime = new Date(enrollmentPeriod[0]);
          this.endTime = new Date(enrollmentPeriod[1]);
        }
      },
      error: (err) => {
        console.error('Error fetching enrollment period:', err);
      }
    });

    this.selectedUserService.selectedUser$.subscribe(user => {
      this.selectedUser = user;
      console.log(this.selectedUser)
      if (this.selectedUser && ((this.selectedUser as Student).role === 'student')) {
        this.loadCoursesSameYearForStudent((this.selectedUser as Student).studyYear)
      }
    });
    this.cdr.detectChanges();

  }

  private loadCoursesSameYearForStudent(studyYear: number): void {
    this.courseService.getCoursesByStudyYear(studyYear).pipe(
      switchMap(courses => {
        this.courses=courses
        this.electiveCourses = this.courses.filter(course => course.category === 'elective');
        const applicationObservables = this.courses.map(course =>
          this.courseService.getNrOfApplicationsForCourse(course.id).pipe(
            map(count => ({ courseId: course.id, count }))
          )
        );

        return forkJoin(applicationObservables);
      })
    ).subscribe(applicationCounts => {
      applicationCounts.forEach(appCount => {
        this.courseApplicationCounts[appCount.courseId] = appCount.count;
      });
      console.log(this.electiveCourses)
    });
  }

  setEnrollmentPeriod(startTime: string, endTime: string): void {
    this.enrollmentAdministrationService.setEnrollmentPeriod(startTime, endTime).subscribe({
      next: () => {
        // console.log('Enrollment period set successfully');
      },
      error: (err) => {
        console.error('Error setting enrollment period:', err);
      }
    });
  }

  drop(event: CdkDragDrop<Course[]>): void {
    moveItemInArray(this.electiveCourses, event.previousIndex, event.currentIndex);
    console.log('Elective courses reordered:', this.electiveCourses);
  }

}
