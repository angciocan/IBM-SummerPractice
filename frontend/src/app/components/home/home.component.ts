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
import { forkJoin, map, switchMap } from "rxjs";
import { EnrollmentService } from "../../services/enrollment.service";

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
  students: Student[] = [];
  filteredStudents: Student[] = [];
  mandatoryCourses: Course[] = [];
  electiveCourses: Course[] = [];
  courseApplicationCounts: { [courseId: number]: number } = {};

  // Filter values
  selectedYear: number | null = null;
  selectedFaculty: string | null = null;
  minGrade: number | null = null;

  // For dropdowns
  uniqueStudyYears: number[] = [];
  uniqueFacultySections: string[] = [];

  constructor(
    private selectedUserService: SelectedUserService,
    private studentService: StudentService,
    private cdr: ChangeDetectorRef,
    private courseService: CourseService,
    private enrollmentService: EnrollmentService
  ) {}

  ngOnInit(): void {
    this.selectedUserService.selectedUser$.subscribe(user => {
      this.selectedUser = user;
      this.studentService.getAllStudents().subscribe(data => {
        this.students = data;
        this.filteredStudents = data; // Initially, all students are displayed
        this.setUniqueFilters(); // Set unique study years and faculty sections
      });

      if (this.selectedUser && this.isStudent(this.selectedUser)) {
        this.loadCoursesSameYearForStudent((this.selectedUser as Student).studyYear);
      }

      if (this.selectedUser && this.isTeacher(this.selectedUser)) {
        this.courses = (this.selectedUser as Teacher).courses;
      }
    });
    this.cdr.detectChanges();
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
