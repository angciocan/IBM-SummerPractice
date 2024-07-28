import {User} from "./user";

export interface Student extends User {
  grade:number;
  study_year:number;
  faculty_section:string;
}
