import {Teacher} from "./teacher";

export interface Course {
  id: number;
  courseName: string;
  maxStudents: number;
  studyYear: number;
  category: string;
  dayOfWeek: string;
  time: string;
  teacher: Teacher;
}

