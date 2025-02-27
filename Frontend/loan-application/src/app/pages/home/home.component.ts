import { Component } from '@angular/core';
import { NavComponent } from '../../nav/nav.component';
import { CommonModule } from '@angular/common';
import { AboutComponent } from '../about/about.component';
import { ContactComponent } from '../contact/contact.component';
import { RouterLink } from '@angular/router';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-home',
  imports: [CommonModule, AboutComponent, ContactComponent, FooterComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {}
