import {EnrollmentStatus} from "../enums/enrollment-status";

export interface Enrollment {
  id: number;
  studentId: number;
  courseId: number;
  status: EnrollmentStatus;
}
