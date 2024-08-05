import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Teacher} from "../interfaces/teacher";
import {Course} from "../interfaces/course";

@Injectable({
  providedIn: 'root'
})
export class TeacherService {

  private apiUrl = 'http://localhost:8080/teacher';

  constructor(private http: HttpClient) { }

  getAllTeachers(): Observable<Teacher[]> {
    return this.http.get<Teacher[]>(`${this.apiUrl}/`).pipe(
      map(teachers => teachers.map(teacher => ({ ...teacher, role: 'teacher' })))
    );
  }

  getCoursesForTeacher(teacherId: number): Observable<Course[]> {
    return this.http.get<Course[]>(`http://localhost:8080/enrollment/courses-of-the-student/${teacherId}`);
  }
}
