import {User} from "./user";

export interface Student extends User {
  grade:number;
  studyYear:number;
  facultySection:string;
  role: 'student';

}
