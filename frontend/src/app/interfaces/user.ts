export interface User {
  studyYear: number;
  id: number;
  name: string;
  role: 'student' | 'teacher' | 'admin';
}
