import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { UserService } from '../services/user.service';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { ToastService, AngularToastifyModule } from 'angular-toastify';

@Component({
  selector: 'app-login',
  imports: [
    RouterLink,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    AngularToastifyModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  // providers: [ToastService],
})
export class LoginComponent {
  authRequest: any;
  private isBrowser: boolean;
  hidePassword = true;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private us: UserService,
    private router: Router,
    private toastService: ToastService,
    @Inject(PLATFORM_ID) private platformId: object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
    this.authRequest = this.fb.group({
      username: [],
      password: [],
    });
  }
  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  fnLogin() {
    if (this.isBrowser) {
      this.us.fnLogin(this.authRequest.value).subscribe(
        (response) => {
          var res = { token: '', role: '' };
          res = <any>response;
          if (res.token) {
            localStorage.setItem('jwt', res.token);
            localStorage.setItem('role', res.role);
            this.errorMessage = '';
            if (res.role === 'BANKMANAGER') {
              this.toastService.success(
                'Login successful, Welcome Bank Manager'
              );
            } else {
              this.toastService.success('Login successful!');
            }
            setTimeout(() => {
              this.router.navigateByUrl('');
            }, 2000);
          }
        },
        (error) => {
          this.errorMessage = 'Login failed: Invalid credentials';
          // this.toastService.error('Login failed: Invalid credentials');
        }
      );
    } else {
      console.warn('localStorage is not available on the server.');
    }
  }
}
