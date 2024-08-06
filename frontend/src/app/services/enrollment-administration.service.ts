import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Enrollment} from "../interfaces/enrollment";
import {EnrollmentAdministration} from "../interfaces/enrollment-administration";

@Injectable({
  providedIn: 'root'
})
export class EnrollmentAdministrationService {

  private apiUrl = 'http://localhost:8080/enrollmentAdministration';

  constructor(private http: HttpClient) { }

  getAllEnrollmentAdministration(): Observable<EnrollmentAdministration[]> {
    return this.http.get<EnrollmentAdministration[]>((`${this.apiUrl}/`));
  }
  setEnrollmentPeriod(startTime: string, endTime: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/setEnrollmentPeriod/${startTime}/${endTime}`, null);
  }

  getEnrollmentPeriod(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/getEnrollmentPeriod`);
  }
}
