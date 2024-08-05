import {User} from "./user";
import {Course} from "./course";

export interface Student extends User {
  grade:number;
  studyYear:number;
  facultySection:string;
  courses:Course[];
  role: 'student';

}
