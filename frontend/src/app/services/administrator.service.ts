import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Admin} from "../interfaces/admin";
import {Course} from "../interfaces/course";

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
