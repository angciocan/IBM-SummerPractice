import {User} from "./user";
import {Course} from "./course";

export interface Teacher extends User{
  role: 'teacher';
  courses: Course[];
}
