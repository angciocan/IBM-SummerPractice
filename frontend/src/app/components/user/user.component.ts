import { Component } from '@angular/core';
import {User} from "../../interfaces/user";
import {Student} from "../../interfaces/student";
import {Teacher} from "../../interfaces/teacher";
import {Admin} from "../../interfaces/admin";
import {StudentService} from "../../services/student.service";
import {SelectedUserService} from "../../services/selected-user.service";
import {CommonModule} from "@angular/common";
import {TeacherService} from "../../services/teacher.service";
import {AdministratorService} from "../../services/administrator.service";

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent {
  students: Student[] = [];
  teachers: Teacher[] = [];
  admins: Admin[] = [];

  constructor(
    private studentService: StudentService,
    private teacherService: TeacherService,
    private administratorService: AdministratorService,
    private selectedUserService: SelectedUserService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.studentService.getAllStudents().subscribe(data => this.students = data);
    this.teacherService.getAllTeachers().subscribe(data => this.teachers = data);
    this.administratorService.getAllAdministrators().subscribe(data => this.admins = data);
  }

  selectUser(user: User): void {
    this.selectedUserService.setSelectedUser(user);
  }

  getSelectedUser(): User | null {
    return this.selectedUserService.getSelectedUser();
  }

}
