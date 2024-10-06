import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Admin} from "../interfaces/admin";
import {Course} from "../interfaces/course";
import {Student} from "../interfaces/student";
import {Teacher} from "../interfaces/teacher";

@Injectable({
  providedIn: 'root'
})
export class AdministratorService {

  private apiUrl = 'http://localhost:8080/administrator';

  constructor(private http: HttpClient) { }

  getAllAdministrators(): Observable<Admin[]> {
    return this.http.get<Admin[]>((`${this.apiUrl}/`)).pipe(
      map(admins => admins.map(admin => ({ ...admin, role: 'admin' })))
    );
  }

  createCourse(course: Course): Observable<Course> {
    return this.http.post<Course>(`${this.apiUrl}/createCourse`, course);
  }

  updateCourse(id: number, course: Course): Observable<Course> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.post<Course>(`${this.apiUrl}/updateCourse`, course, { params });
  }

  deleteCourse(id: number): Observable<void> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.delete<void>(`${this.apiUrl}/deleteCourse`, { params });
  }

  createStudent(student: Student): Observable<Student>{
    return this.http.post<Student>(`${this.apiUrl}/createStudent`, student);
  }

  updateStudent(id: number, student: Student): Observable<Student> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.post<Student>(`${this.apiUrl}/updateStudent`, student, {params})
  }

  deleteStudent(id: number): Observable<void> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.delete<void>(`${this.apiUrl}/deleteStudent`, {params} );
  }

  createTeacher(teacher: Teacher): Observable<Teacher> {
    return this.http.post<Teacher>(`${this.apiUrl}/createTeacher`,teacher)
  }

  updateTeacher(id:number, teacher: Teacher): Observable<Teacher>{
    let params = new HttpParams().set('id',id.toString())
    return this.http.post<Teacher>(`${this.apiUrl}/updateTeacher`, teacher, {params})
  }

  deleteTeacher(id: number): Observable<void> {
    let params = new HttpParams().set('id',id.toString())
    return this.http.post<void>(`${this.apiUrl}/deleteTeacher`, {params})
  }


}
