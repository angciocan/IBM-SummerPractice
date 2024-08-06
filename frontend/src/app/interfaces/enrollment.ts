import {EnrollmentStatus} from "../enums/enrollment-status";

interface Enrollment {
  id: number;
  studentId: number;
  courseId: number;
  status: EnrollmentStatus;
}
