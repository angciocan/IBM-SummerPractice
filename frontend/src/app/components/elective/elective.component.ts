import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { EnrollmentAdministrationService } from '../../services/enrollment-administration.service';
import { EnrollmentAdministration } from '../../interfaces/enrollment-administration';
import { Course } from '../../interfaces/course';
import { SelectedUserService } from '../../services/selected-user.service';
import { Student } from '../../interfaces/student';
import { User } from '../../interfaces/user';
import { forkJoin, map } from 'rxjs';
import { CdkDragDrop, moveItemInArray, CdkDropList, CdkDrag } from '@angular/cdk/drag-drop';
import { CourseService } from '../../services/course.service';
import { NgClass, NgForOf, NgIf } from '@angular/common';
import { EnrollmentService } from '../../services/enrollment.service';
import {EnrollmentManagementService} from "../../services/enrollment-management.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-elective',
  standalone: true,
  templateUrl: './elective.component.html',
  imports: [
    NgForOf,
    NgIf,
    CdkDropList,
    CdkDrag,
    NgClass,
  ],
  styleUrls: ['./elective.component.scss'],
})
export class ElectiveComponent implements OnInit {
  enrollmentAdministrations: EnrollmentAdministration[] = [];
  electiveCourses: Course[] = [];
  protected selectedUser: User | null = null;
  private courses: Course[] = [];
  courseApplicationCounts: { [courseId: number]: number } = {};
  studentsEnrolled: { [courseId: number]: Student[] } = {};

  constructor(
    private enrollmentAdministrationService: EnrollmentAdministrationService,
    private selectedUserService: SelectedUserService,
    private courseService: CourseService,
    private enrollmentService: EnrollmentService,
    private enrollmentManagementService: EnrollmentManagementService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.enrollmentAdministrationService.getAllEnrollmentAdministration().subscribe({
      next: (enrollments: EnrollmentAdministration[]) => {
        this.enrollmentAdministrations = enrollments;
      },
      error: (err) => {
        console.error('Error fetching enrollments:', err);
      },
    });

    this.selectedUserService.selectedUser$.subscribe((user) => {
      this.selectedUser = user;
      if (this.selectedUser && (this.selectedUser as Student).role === 'student') {
        this.loadCoursesSameYearForStudent((this.selectedUser as Student).studyYear);
      }
    });
  }

  private updateApplicationCounts(): void {
    const applicationObservables = this.courses.map((course) =>
      this.enrollmentService.getNrOfApplicationsForCourse(course.id).pipe(
        map((count) => ({ courseId: course.id, count }))
      )
    );

    forkJoin(applicationObservables).subscribe((applicationCounts) => {
      applicationCounts.forEach((appCount) => {
        this.courseApplicationCounts[appCount.courseId] = appCount.count;
      });
      this.cdr.detectChanges();
    });
  }

  private loadCoursesSameYearForStudent(studyYear: number): void {
    this.courseService.getCoursesByStudyYear(studyYear).subscribe((courses) => {
      this.courses = courses;
      this.electiveCourses = this.courses.filter((course) => course.category === 'elective');
      this.loadStudentsEnrolledInCourses();
      this.updateApplicationCounts();
      console.log(this.electiveCourses);
    });
  }

  private loadStudentsEnrolledInCourses(): void {
    const courseObservables = this.electiveCourses.map((course) =>
      this.enrollmentService.getStudentPendingEnrollmentToCourse(course.id).pipe(
        map((students) => ({ courseId: course.id, students }))
      )
    );

    forkJoin(courseObservables).subscribe((courseStudents) => {
      courseStudents.forEach(({ courseId, students }) => {
        this.studentsEnrolled[courseId] = students;
        console.log(this.studentsEnrolled);
      });
      this.cdr.detectChanges();
    });
  }

  createEnrollment(studentId: number, courseId: number): void {
    this.enrollmentService.createEnrollment(studentId, courseId).subscribe({
      next: () => {
        console.log('Enrollment created successfully');
      },
      error: (err) => {
        console.error('Error creating enrollment:', err);
      },
    });
  }

  createEnrollmentForSelectedCourses(): void {
    if (this.selectedUser && (this.selectedUser as Student).role === 'student') {
      const studentId = (this.selectedUser as Student).id;
      const studyYear = (this.selectedUser as Student).studyYear;

      const eligibleCourses = this.electiveCourses.filter((course) => course.studyYear === studyYear);

      if (eligibleCourses.length < studyYear) {
        console.warn("Not enough eligible courses available for the student's study year");
        return;
      }

      for (let i = 0; i < studyYear; i++) {
        this.createEnrollment(studentId, eligibleCourses[i].id);
      }

      console.log(`Enrolled student ${studentId} in ${studyYear} courses.`);
    } else {
      console.warn('No student selected or the selected user is not a student');
    }
  }

  processPendingEnrollment(): void {
    this.enrollmentManagementService.processPendingEnrollments().subscribe({
      next: (response) => {
        console.log('Enrollments_processed_successfully', response);
      },
      error: (err: HttpErrorResponse) => {
        console.error('Error processing enrollments:', err);
        if (err.error instanceof ErrorEvent) {
          console.error('Client-side error:', err.error.message);
        } else {
          console.error(`Server-side error. Status: ${err.status}, Body: `, err.error);
        }
      },
    });
  }


  drop(event: CdkDragDrop<Course[]>): void {
    moveItemInArray(this.electiveCourses, event.previousIndex, event.currentIndex);
    console.log('Elective courses reordered:', this.electiveCourses);
  }
}
