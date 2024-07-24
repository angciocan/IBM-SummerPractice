import { Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {StudentComponent} from "./components/student/student.component";
import {ElectiveComponent} from "./components/elective/elective.component";

export const routeConfig: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Home page'
  },
  {
    path: 'home',
    component: HomeComponent,
    title: 'Home page'
  },
  {
    path: 'student',
    component: StudentComponent,
    title: 'Student details'
  },
  {
    path: 'elective',
    component: ElectiveComponent,
    title: 'Elective details'
  },
];

export default routeConfig
