import {Component, OnInit} from '@angular/core';
import {User} from "../../interfaces/user";
import {Student} from "../../interfaces/student";
import {Teacher} from "../../interfaces/teacher";
import {Admin} from "../../interfaces/admin";
import {StudentService} from "../../services/student.service";
import {SelectedUserService} from "../../services/selected-user.service";
import {CommonModule} from "@angular/common";
import {TeacherService} from "../../services/teacher.service";
import {AdministratorService} from "../../services/administrator.service";
import {Course} from "../../interfaces/course";
import {EnrollmentService} from "../../services/enrollment.service";
import {Enrollment} from "../../interfaces/enrollment";
import {Observable} from "rxjs";

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent implements OnInit{
  students: Student[] = [];
  teachers: Teacher[] = [];
  admins: Admin[] = [];
  userCourses: Course[] = [];
  user: User | null = null;
  enrollments: { [key: number]: Enrollment } = {};

  constructor(
    private studentService: StudentService,
    private teacherService: TeacherService,
    private administratorService: AdministratorService,
    private selectedUserService: SelectedUserService,
    private enrollmentService: EnrollmentService
  ) {}

  ngOnInit(): void {
    this.loadUsers();

    this.user = this.selectedUserService.getSelectedUser()
    if (this.user) {
      this.loadUserCourses(this.user);
      console.log(this.user)
    }
  }

  loadUsers(): void {
    this.studentService.getAllStudents().subscribe(data => this.students = data);
    this.teacherService.getAllTeachers().subscribe(data => this.teachers = data);
    this.administratorService.getAllAdministrators().subscribe(data => this.admins = data);
  }

  selectUser(user: User): void {
    this.selectedUserService.setSelectedUser(user);
    this.user = user
    this.loadUserCourses(user);
    this.loadEnrollments();
    // console.log(user)
  }

  clearSelectedUser(): void {
    this.selectedUserService.clearSelectedUser();
    this.userCourses = [];
  }

  getSelectedUser(): User | null {
    return this.selectedUserService.getSelectedUser();
  }

  private loadUserCourses(user: User): void {
    if (user.role === 'student') {
      this.loadCoursesForStudent(user.id);
    } else if (user.role === 'teacher') {
      this.userCourses = (user as Teacher).courses;
      console.log(this.userCourses)
    }
  }

  private loadCoursesForStudent(studentId: number): void {
    this.enrollmentService.getCoursesForStudent(studentId).subscribe(courses => {
      this.userCourses = courses;
      console.log(this.userCourses)
    });
  }

  private loadEnrollments(): void {
      this.userCourses.forEach(course => {
        if(this.user) {
          console.log(this.user.id)
          let id = 0
          this.getEnrollment(this.user.id, course.id).subscribe(enrollment => {
            console.log(enrollment)
            this.enrollments[id] = enrollment;
            id++
          });
        }
      });
    console.log(this.enrollments)
  }

  getEnrollment(studentId:number, courseId: number): Observable<Enrollment>{
    return this.enrollmentService.getEnrollment(studentId,courseId)
  }

}
