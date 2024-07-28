import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {User} from "../interfaces/user";
import {Teacher} from "../interfaces/teacher";
import {Admin} from "../interfaces/admin";
import {Student} from "../interfaces/student";

@Injectable({
  providedIn: 'root'
})
export class SelectedUserService {

  private selectedUserSubject: BehaviorSubject<User | null> = new BehaviorSubject<User | null>(null);
  public selectedUser$: Observable<User | null> = this.selectedUserSubject.asObservable();

  constructor() {}

  setSelectedUser(user: User): void {
    this.selectedUserSubject.next(user);
  }

  getSelectedUser(): User | null {
    return this.selectedUserSubject.value;
  }

  clearSelectedUser(): void {
    this.selectedUserSubject.next(null);
  }
}
