import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Student} from "../interfaces/student";
import {Admin} from "../interfaces/admin";

@Injectable({
  providedIn: 'root'
})
export class AdministratorService {

  private apiUrl = 'http://localhost:8080/administrator';

  constructor(private http: HttpClient) { }

  getAllAdministrators(): Observable<Admin[]> {
    return this.http.get<Admin[]>((`${this.apiUrl}/`));
  }
}
