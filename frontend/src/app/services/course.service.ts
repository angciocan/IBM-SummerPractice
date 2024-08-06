import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Course} from "../interfaces/course";

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private apiUrl = 'http://localhost:8080/course';
  private apiUrl2 = 'http://localhost:8080/enrollment'

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
  getNrOfApplicationsForCourse(courseId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl2}/nr-of-current-applications/${courseId}`);
  }
}
