import { Component, OnInit } from '@angular/core';
import { EnrollmentAdministrationService } from '../../services/enrollment-administration.service';
import { EnrollmentAdministration } from '../../interfaces/enrollment-administration';

@Component({
  selector: 'app-elective',
  standalone: true,
  templateUrl: './elective.component.html',
  styleUrls: ['./elective.component.scss']
})
export class ElectiveComponent implements OnInit {

  enrollmentAdministrations: EnrollmentAdministration[] = [];
  startTime: Date | null = null;
  endTime: Date | null = null;

  constructor(private enrollmentAdministrationService: EnrollmentAdministrationService) {}

  ngOnInit(): void {
    this.enrollmentAdministrationService.getAllEnrollmentAdministration().subscribe({
      next: (enrollments: EnrollmentAdministration[]) => {
        console.log('Enrollments:', enrollments);
        this.enrollmentAdministrations = enrollments;
        console.log(this.enrollmentAdministrations);
        this.enrollmentAdministrationService.getEnrollmentPeriod()
      },
      error: (err) => {
        console.error('Error fetching enrollments:', err);
      }
    });

    this.enrollmentAdministrationService.getEnrollmentPeriod().subscribe({
      next: (enrollmentPeriod: string[]) => {
        if (enrollmentPeriod.length === 2) {
          this.startTime = new Date(enrollmentPeriod[0]);
          this.endTime = new Date(enrollmentPeriod[1]);
          console.log('Enrollment Period:', this.startTime, this.endTime);
        }
      },
      error: (err) => {
        console.error('Error fetching enrollment period:', err);
      }
    });
  }

  setEnrollmentPeriod(startTime: string, endTime: string): void {
    this.enrollmentAdministrationService.setEnrollmentPeriod(startTime, endTime).subscribe({
      next: () => {
        console.log('Enrollment period set successfully');
      },
      error: (err) => {
        console.error('Error setting enrollment period:', err);
      }
    });
  }
}
