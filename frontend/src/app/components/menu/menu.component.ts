import { Component } from '@angular/core';
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {
  activeLink: string = '';

  constructor(private router: Router) {
    this.activeLink = this.router.url;
  }

  navigate(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const url = selectElement.value;
    this.setActive(url);
  }

  setActive(link: string): void {
    this.activeLink = link;
    this.router.navigateByUrl(link);
  }
}
