import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {User} from "../../interfaces/user";
import {SelectedUserService} from "../../services/selected-user.service";
import {CommonModule} from "@angular/common";
import {ReplaceNullWithTextPipe} from "../../pipes/replace-null-with-text.pipe";
import {StudentService} from "../../services/student.service";
import {Course} from "../../interfaces/course";
import {Student} from "../../interfaces/student";
import { CdkDragDrop, moveItemInArray, DragDropModule } from '@angular/cdk/drag-drop';
import {Teacher} from "../../interfaces/teacher";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ReplaceNullWithTextPipe, DragDropModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  selectedUser: User | null = null;
  courses: Course[] = [];
  electiveCourses: string[] = [
    'Istoria Artei',
    'Programare Avansată',
    'Psihologie',
    'Fotografie Digitală',
    'Marketing Online'
  ];

  constructor(
    private selectedUserService: SelectedUserService,
    private studentService: StudentService,
    private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.selectedUserService.selectedUser$.subscribe(user => {
      this.selectedUser = user;
      console.log(user)
      if (this.selectedUser && ((this.selectedUser as Student).role === 'student')) {
        this.loadCoursesForStudent(this.selectedUser.id);
      }
      if (this.selectedUser && ((this.selectedUser as Teacher).role === 'teacher')) {
        this.courses = (this.selectedUser as Teacher).courses
        console.log(this.courses)
      }
    });
    this.cdr.detectChanges();
  }

  private loadCoursesForStudent(studentId: number): void {
    this.studentService.getCoursesForStudent(studentId).subscribe(courses => {
      this.courses = courses;
    });
  }

  isStudent(user: User | null): user is Student {
    return user?.role === 'student';
  }

  drop(event: CdkDragDrop<string[]>): void {
    moveItemInArray(this.electiveCourses, event.previousIndex, event.currentIndex);
  }
}
