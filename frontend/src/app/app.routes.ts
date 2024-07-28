import { Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {StudentComponent} from "./components/student/student.component";
import {ElectiveComponent} from "./components/elective/elective.component";
import {UserComponent} from "./components/user/user.component";

export const routeConfig: Routes = [
  {
    path: '',
    component: StudentComponent,
    title: 'Student page'
  },
  {
    path: 'home',
    component: HomeComponent,
    title: 'Home page'
  },
  {
    path: 'user',
    component: UserComponent,
    title: 'User page'
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
