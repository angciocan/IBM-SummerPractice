import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {StudentComponent} from "./components/student/student.component";
import {MenuComponent} from "./components/menu/menu.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, StudentComponent, MenuComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
}
