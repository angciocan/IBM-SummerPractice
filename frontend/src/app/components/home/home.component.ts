import {Component, OnInit} from '@angular/core';
import {User} from "../../interfaces/user";
import {SelectedUserService} from "../../services/selected-user.service";
import {CommonModule} from "@angular/common";
import {ReplaceNullWithTextPipe} from "../../pipes/replace-null-with-text.pipe";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ReplaceNullWithTextPipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  selectedUser: User | null = null;

  constructor(private selectedUserService: SelectedUserService) {}

  ngOnInit(): void {
    this.selectedUserService.selectedUser$.subscribe(user => this.selectedUser = user);
  }

}
