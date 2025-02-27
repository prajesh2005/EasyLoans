import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { LogoutComponent } from './pages/logout/logout.component';
import { GetAllApplicationsComponent } from './get-all-applications/get-all-applications.component';
import { HomeComponent } from './pages/home/home.component';
import { CreateApplicationComponent } from './create-application/create-application.component';
import { NavComponent } from './nav/nav.component';
import { GetDocumentTypesComponent } from './get-document-types/get-document-types.component';
import { ProcessLoanApplicationComponent } from './process-loan-application/process-loan-application.component';
import { AuthGuard } from './auth.guard';
import { AboutComponent } from './pages/about/about.component';
import { ContactComponent } from './pages/contact/contact.component';
import { FooterComponent } from './pages/footer/footer.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'footer', component: FooterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'logout', component: LogoutComponent },
  {
    path: 'nav',
    component: NavComponent,
    // canActivate: [AuthGuard],
  },
  {
    path: 'create',
    component: CreateApplicationComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'app',
    component: GetAllApplicationsComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'doc',
    component: GetDocumentTypesComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'process-loan/:id',
    component: ProcessLoanApplicationComponent,
    canActivate: [AuthGuard],
  },
  { path: '**', component: PageNotFoundComponent },
];
