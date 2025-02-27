import { routes } from './app/app.routes';
import { provideRouter } from '@angular/router';
import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import {
  provideHttpClient,
  withInterceptors,
  withFetch,
} from '@angular/common/http';
import { authInterceptor } from './app/auth.interceptor';

// bootstrapApplication(AppComponent, appConfig)
//   .catch((err) => console.error(err));

// ----with interceptor
bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withFetch()),
    provideHttpClient(withInterceptors([authInterceptor])),
    provideRouter(routes),
  ],
}).catch((err) => console.error(err));
