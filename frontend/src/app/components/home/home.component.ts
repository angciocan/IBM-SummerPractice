import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {User} from "../../interfaces/user";
import {SelectedUserService} from "../../services/selected-user.service";
import {CommonModule} from "@angular/common";
import {ReplaceNullWithTextPipe} from "../../pipes/replace-null-with-text.pipe";
import {StudentService} from "../../services/student.service";
import {Course} from "../../interfaces/course";
import {Student} from "../../interfaces/student";
import { CdkDragDrop, moveItemInArray, DragDropModule } from '@angular/cdk/drag-drop';
import {Teacher} from "../../interfaces/teacher";
import {CourseService} from "../../services/course.service";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ReplaceNullWithTextPipe, DragDropModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  selectedUser: User | null = null;
  courses: Course[] = [];
  mandatoryCourses: Course[] = [];
  electiveCourses: Course[] = [];
  constructor(
    private selectedUserService: SelectedUserService,
    private studentService: StudentService,
    private cdr: ChangeDetectorRef,
    private courseService: CourseService) {}

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

  private loadCoursesForStudent(studentId: number): void {
    this.studentService.getCoursesForStudent(studentId).subscribe(courses => {
      this.courses = courses;
      // console.log(courses)
      // this.mandatoryCourses = this.courses.filter(course => course.category == 'mandatory');
      // this.electiveCourses = this.courses.filter(course => course.category == 'elective');
    });
  }
  private loadCoursesSameYearForStudent(studyYear: number): void {
    this.courseService.getCoursesByStudyYear(studyYear).subscribe(courses => {
      this.courses = courses;
      this.mandatoryCourses = this.courses.filter(course => course.category == 'mandatory');
      this.electiveCourses = this.courses.filter(course => course.category == 'elective');
      console.log(this.mandatoryCourses)
    });
  }

  isStudent(user: User | null): user is Student {
    return user?.role === 'student';
  }

}
