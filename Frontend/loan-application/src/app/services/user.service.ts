import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  fnLogin(loginRequest: any) {
    return this.http.post(
      'http://localhost:5001/api/users/login',
      loginRequest
    );
  }

  fnSignup(signupRequest: any) {
    return this.http.post(
      'http://localhost:5001/api/users/signup',
      signupRequest
    );
  }
}
