import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, Observable} from 'rxjs';
import { Student } from '../interfaces/student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private apiUrl = 'http://localhost:8080/student';

  constructor(private http: HttpClient) { }

  getAllStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.apiUrl}/`).pipe(
      map(students => students.map(student => ({ ...student, role: 'student' })))
    );
  }
  getStudentById(id: number): Observable<Student> {
    return this.http.get<Student>(`${this.apiUrl}/getStudent/${id}`, )
  }

}
