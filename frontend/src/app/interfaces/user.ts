export interface User {
  id: number;
  name: string;
  role: 'student' | 'teacher' | 'admin';
}
