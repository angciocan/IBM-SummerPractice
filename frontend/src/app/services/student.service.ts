import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {map, Observable} from 'rxjs';
import { Student } from '../interfaces/student';
import {Course} from "../interfaces/course";
@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private apiUrl = 'http://localhost:8080/student';

  constructor(private http: HttpClient) { }

  getAllStudents(): Observable<Student[]> {
    return this.http.get<Student[]>("http://localhost:8080/student/getStudent").pipe(
      map(students => students.map(student => ({ ...student, role: 'student' })))
    );
  }

  getCoursesForStudent(studentId: number): Observable<Course[]> {
    return this.http.get<Course[]>(`http://localhost:8080/enrollment/courses-of-the-student/${studentId}`);
  }
}
