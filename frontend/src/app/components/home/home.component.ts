import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { User } from "../../interfaces/user";
import { SelectedUserService } from "../../services/selected-user.service";
import { CommonModule } from "@angular/common";
import { ReplaceNullWithTextPipe } from "../../pipes/replace-null-with-text.pipe";
import { StudentService } from "../../services/student.service";
import { Course } from "../../interfaces/course";
import { Student } from "../../interfaces/student";
import { Teacher } from "../../interfaces/teacher";
import { CourseService } from "../../services/course.service";
import { EnrollmentService } from "../../services/enrollment.service";
import {forkJoin, map, switchMap} from "rxjs";
import {FormsModule} from "@angular/forms";
import {AdministratorService} from "../../services/administrator.service";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ReplaceNullWithTextPipe, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  selectedUser: User | null = null;
  students: Student[] = [];
  filteredStudents: Student[] = [];
  studentsEnrolledToCoursesMap: { [courseId: number]: Student[] } = {};
  allCourses: Course[] = [];
  courses: Course[] = [];
  mandatoryCourses: Course[] = [];
  electiveCourses: Course[] = [];
  courseApplicationCounts: { [courseId: number]: number } = {};

  selectedYear: number | null = null;
  selectedFaculty: string | null = null;
  minGrade: number | null = null;

  uniqueStudyYears: number[] = [];
  uniqueFacultySections: string[] = [];

  newCourse: Course = {
    id: 0,
    courseName: '',
    maxStudents: 0,
    studyYear: 0,
    category: '',
    dayOfWeek: '',
    time: '',
    teacherDTO: null
  };
  protected selectedCourse: Course | null  = null;

  constructor(
    private selectedUserService: SelectedUserService,
    private studentService: StudentService,
    private administratorService: AdministratorService,
    private cdr: ChangeDetectorRef,
    private courseService: CourseService,
    private enrollmentService: EnrollmentService
  ) {}

  ngOnInit(): void {
    this.selectedUserService.selectedUser$.subscribe(user => {
      this.selectedUser = user;
      this.studentService.getAllStudents().subscribe(data => {
        this.students = data;
        this.filteredStudents = data;
        this.setUniqueFilters();
      });
      this.courseService.getAllCourses().subscribe(courses => {
        this.allCourses = courses;
      })

      if (this.selectedUser && this.isStudent(this.selectedUser)) {
        this.loadCoursesSameYearForStudent((this.selectedUser as Student).studyYear);
      }

      if (this.selectedUser && this.isTeacher(this.selectedUser)) {
        this.courses = (this.selectedUser as Teacher).courses;
      }
    });
    this.cdr.detectChanges();
  }


  onCreateCourse(): void {
    this.administratorService.createCourse(this.newCourse).subscribe(
      createdCourse => {
        console.log('Course created successfully:', createdCourse);
        this.newCourse = {
          id: 0,
          courseName: '',
          maxStudents: 0,
          studyYear: 0,
          category: '',
          dayOfWeek: '',
          time: '',
          teacherDTO: null
        };
      },
      error => {
        console.error('Error creating course:', error);
      }
    );
  }

  getStudentsEnrolledToCourse(courseId: number): void {
    this.enrollmentService.getStudentsEnrolledToCourse(courseId).subscribe(
      students => {
        this.studentsEnrolledToCoursesMap[courseId] = students;
        console.log(`Students enrolled for course ${courseId}:`, students);
      },
      error => {
        console.error('Error getting students for course:', error);
      }
    );
  }

  onUpdateCourse(): void {
    if (this.selectedCourse && this.selectedCourse.id) {
      this.administratorService.updateCourse(this.selectedCourse.id, this.selectedCourse).subscribe(
        updatedCourse => {
          console.log('Course updated successfully:', updatedCourse);
          this.selectedCourse = null;
        },
        error => {
          console.error('Error updating course:', error);
        }
      );
    } else {
      console.error('Selected course or course ID is missing');
    }
  }

  getCourseById(courseId: any): void {
    const id = Number(courseId);
    if (!id) {
      console.error('Invalid course ID');
      return;
    }

    this.courseService.getCourseById(id).subscribe(
      course => {
        this.selectedCourse = course;
        console.log('Course loaded:', this.selectedCourse);
      },
      error => {
        console.error('Error fetching course:', error);
      }
    );
  }

  onDeleteCourse(): void {
    if (this.selectedCourse && this.selectedCourse.id) {
      this.administratorService.deleteCourse(this.selectedCourse.id).subscribe(
        () => {
          console.log('Course deleted successfully');
          this.allCourses = this.allCourses.filter(course => course.id !== this.selectedCourse?.id);
          this.selectedCourse = null;
        },
        error => {
          console.error('Error deleting course:', error);
        }
      );
    } else {
      console.error('Selected course or course ID is missing');
    }
  }


  onYearFilter(event: any): void {
    const value = event.target.value;
    this.selectedYear = value ? parseInt(value, 10) : null;
    this.applyFilters();
  }

  onFacultyFilter(event: any): void {
    const value = event.target.value;
    this.selectedFaculty = value || null;
    this.applyFilters();
  }

  onGradeFilter(event: any): void {
    const value = event.target.value;
    this.minGrade = value ? parseFloat(value) : null;
    this.applyFilters();
  }

  applyFilters(): void {
    this.filteredStudents = this.students.filter(student => {
      const matchesYear = this.selectedYear ? student.studyYear === this.selectedYear : true;
      const matchesFaculty = this.selectedFaculty ? student.facultySection === this.selectedFaculty : true;
      const matchesGrade = this.minGrade ? student.grade >= this.minGrade : true;
      return matchesYear && matchesFaculty && matchesGrade;
    });
  }

  private setUniqueFilters(): void {
    this.uniqueStudyYears = [...new Set(this.students.map(s => s.studyYear))];
    this.uniqueFacultySections = [...new Set(this.students.map(s => s.facultySection))];
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

  isAdmin(user: User | null): boolean {
    return user?.role === 'admin';
  }

  isTeacher(user: User | null): boolean {
    return user?.role === 'teacher';
  }
}
