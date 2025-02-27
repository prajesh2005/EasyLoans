import { CommonModule, TitleCasePipe } from '@angular/common';
import { Component, DoCheck, Inject, PLATFORM_ID } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { LoginService } from '../services/login.service';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-nav',
  imports: [RouterLink, CommonModule],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css',
})
export class NavComponent implements DoCheck {
  status = 'login';
  private isBrowser: boolean;
  isManager: boolean = false;

  constructor(
    private ls: LoginService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  ngDoCheck(): void {
    if (this.isBrowser) {
      this.ls.checkLoginStatusObservable().subscribe((s) => {
        this.status = <any>s;
        this.isManager = this.ls.isManager();
      });
    } else {
      // console.warn('localStorage is not available on the server21.');
    }
  }

  // ----modal----
  logout(): void {
    if (this.isBrowser) {
      localStorage.removeItem('jwt');
      localStorage.removeItem('role');
      // this.status = 'login';
    }
  }
  isLoggedIn(): boolean {
    return this.status !== 'login';
  }

  navigateHome(): void {
    this.router.navigateByUrl('/');
  }
}
