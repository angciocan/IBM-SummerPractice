import { Component, OnInit } from '@angular/core';
import { StudentService } from '../../services/student.service';
import { Student } from '../../interfaces/student';
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
@Component({
  selector: 'app-student',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './student.component.html',
  styleUrl: './student.component.scss'
})
export class StudentComponent implements OnInit {
  students: Student[] = [];

  constructor(private studentService: StudentService) { }

  ngOnInit(): void {
    this.studentService.getAllStudents().subscribe((data: Student[]) => {
      this.students = data;
    }, (error) => {
      console.error('Failed to fetch students', error);
    });
  }
}
