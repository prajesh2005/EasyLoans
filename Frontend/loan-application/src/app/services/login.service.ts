import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { Observable } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private isBrowser: boolean;

  constructor(@Inject(PLATFORM_ID) private platformId: object) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  publish() {
    this.checkLoginStatusObservable();
  }

  isLoggedIn(): boolean {
    return this.checkLoginStatus();
  }

  isManager(): boolean {
    if (this.isBrowser) {
      const role = localStorage.getItem('role');
      return role === 'BANKMANAGER';
    } else {
      // console.warn('localStorage is not available on the server1.');
      return false;
    }
  }

  checkLoginStatus(): boolean {
    if (this.isBrowser) {
      const jwt = localStorage.getItem('jwt');
      return jwt != null && jwt != '';
    } else {
      // console.warn('localStorage is not available on the server2.');
      return false;
    }
  }

  checkLoginStatusObservable(): Observable<string> {
    return new Observable((observer) => {
      setTimeout(() => {
        let status = 'login';
        if (this.isBrowser) {
          const loggedUserName = localStorage.getItem('jwt');
          if (loggedUserName != null && loggedUserName != '') {
            status = 'logout';
          }
        } else {
          // console.warn('localStorage is not available on the server3.');
        }
        observer.next(status);
      }, 100);
    });
  }
}
