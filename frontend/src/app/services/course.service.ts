import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Course} from "../interfaces/course";

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private apiUrl = 'http://localhost:8080/course';

  constructor(private http: HttpClient) { }

  getAllCourses(): Observable<Course[]> {
    return this.http.get<Course[]>((`${this.apiUrl}/`));
  }
  getCoursesByStudyYear(studyYear: number): Observable<Course[]> {
    let params = new HttpParams().set('studyYear', studyYear.toString());
    return this.http.get<Course[]>(`${this.apiUrl}/by-study-year`, { params });
  }
  getCoursesByCategory(category: String): Observable<Course[]> {
    let params = new HttpParams().set('category', category.toString());
    return this.http.get<Course[]>(`${this.apiUrl}/by-category`, { params });
  }

  getCourseById(id: Number): Observable<Course[]> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.get<Course[]>(`${this.apiUrl}/by-id`, { params });
  }



  createCourse(course: Course): Observable<Course> {
    return this.http.post<Course>(`${this.apiUrl}/create`, course);
  }

  updateCourse(id: number, course: Course): Observable<Course> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.post<Course>(`${this.apiUrl}/update`, course, { params });
  }

  deleteCourse(id: number): Observable<void> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.delete<void>(`${this.apiUrl}/delete`, { params });
  }

}
