import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  imports: [],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css',
})
export class LogoutComponent implements OnInit {
  constructor(private ls: LoginService, private router: Router) {}
  ngOnInit(): void {
    localStorage.clear();
    this.ls.publish();
    setTimeout(() => {
      this.router.navigateByUrl('/');
    }, 2000);
  }
}
