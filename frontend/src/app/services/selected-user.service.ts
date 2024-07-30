import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {User} from "../interfaces/user";

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
