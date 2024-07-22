import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from './student';
@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private apiUrl = 'http://localhost:8080/student'; // Adjust the URL to match your Spring Boot endpoint

  constructor(private http: HttpClient) { }

  getStudent(): Observable<Student> {
    return this.http.get<Student>(this.apiUrl);
  }
}
