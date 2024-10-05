import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
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
}
