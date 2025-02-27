// import { HttpClient } from '@angular/common/http';
// import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
// import { isPlatformBrowser } from '@angular/common';

// @Injectable({
//   providedIn: 'root',
// })
// export class LoanService {
//   loan = 'http://localhost:5001/api/loanapplications'; // base URL of loan applications API
//   loanPlan = 'http://localhost:5001/api/loanplans'; // base URL of loan plans API
//   document = 'http://localhost:5001/api/documenttypes'; // base URL of document types API
//   private isBrowser: boolean;

//   constructor(
//     private http: HttpClient,
//     @Inject(PLATFORM_ID) private platformId: object
//   ) {
//     this.isBrowser = isPlatformBrowser(platformId);
//   }

//   fnReadToken() {
//     if (this.isBrowser) {
//       var jwt = localStorage.getItem('jwt');
//       if (jwt != null && jwt != '') {
//         return jwt;
//       }
//     } else {
//       // console.warn('localStorage is not available on the server11.');
//     }
//     return 'error';
//   }

//   fnAddApplication(formData: FormData) {
//     var token = this.fnReadToken();
//     return this.http.post(this.loan, formData, {
//       headers: { Authorization: `Bearer ${token}` },
//       responseType: 'text',
//     });
//   }

//   fnRetriveAllApplications() {
//     var token = this.fnReadToken();
//     return this.http.get(this.loan, {
//       headers: { Authorization: `Bearer ${token}` },
//     });
//   }

//   fnFindApplicationById(id: number) {
//     var token = this.fnReadToken();
//     return this.http.get(this.loan + '/' + id, {
//       headers: { Authorization: `Bearer ${token}` },
//     });
//   }

//   fnFindLoanPlanById(id: number) {
//     var token = this.fnReadToken();
//     return this.http.get(this.loanPlan + '/' + id, {
//       headers: { Authorization: `Bearer ${token}` },
//     });
//   }

//   fnUpdateApplication(application: any) {
//     var token = this.fnReadToken();
//     // console.log('Token:', token);
//     return this.http.put(
//       `${this.loan}/${application.applicationId}`,
//       application,
//       {
//         headers: { Authorization: `Bearer ${token}` },
//       }
//     );
//   }

//   fnRetriveDocumentTypes() {
//     var token = this.fnReadToken();
//     return this.http.get(this.document, {
//       headers: { Authorization: `Bearer ${token}` },
//     });
//   }

//   fnGetSupportingDocument(applicationId: number) {
//     var token = this.fnReadToken();
//     return this.http.get(`${this.loan}/supportingDocuments/${applicationId}`, {
//       headers: { Authorization: `Bearer ${token}` },
//       responseType: 'blob',
//     });
//   }
// }

// ----with interceptor
import { HttpClient } from '@angular/common/http';
import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class LoanService {
  loan = 'http://localhost:5001/api/loanapplications'; // base URL of loan applications API
  loanPlan = 'http://localhost:5001/api/loanplans'; // base URL of loan plans API
  document = 'http://localhost:5001/api/documenttypes'; // base URL of document types API
  private isBrowser: boolean;

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  fnAddApplication(formData: FormData) {
    return this.http.post(this.loan, formData, { responseType: 'text' });
  }

  fnRetriveAllApplications() {
    return this.http.get(this.loan);
  }

  fnFindApplicationById(id: number) {
    return this.http.get(this.loan + '/' + id);
  }

  fnFindLoanPlanById(id: number) {
    return this.http.get(this.loanPlan + '/' + id);
  }

  fnUpdateApplication(application: any) {
    return this.http.put(
      `${this.loan}/${application.applicationId}`,
      application
    );
  }

  fnRetriveDocumentTypes() {
    return this.http.get(this.document);
  }

  fnGetSupportingDocument(applicationId: number) {
    return this.http.get(`${this.loan}/supportingDocuments/${applicationId}`, {
      responseType: 'blob',
    });
  }
}
