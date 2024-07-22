import { Component, OnInit } from '@angular/core';
import { StudentService } from '../student.service';
import { Student } from '../student';
import {CommonModule} from "@angular/common";
@Component({
  selector: 'app-student',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './student.component.html',
  styleUrl: './student.component.scss'
})
export class StudentComponent implements OnInit {
  student: Student | null = null;

  constructor(private studentService: StudentService) { }

  ngOnInit(): void {
    this.studentService.getStudent().subscribe(data => {
      this.student = data;
    });
  }
}
