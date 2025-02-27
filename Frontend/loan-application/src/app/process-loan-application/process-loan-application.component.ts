import { Component, OnInit } from '@angular/core';
import { LoanService } from '../services/loan.service';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ToastService, AngularToastifyModule } from 'angular-toastify';
import { Router } from '@angular/router';
@Component({
  selector: 'app-process-loan-application',
  templateUrl: './process-loan-application.component.html',
  styleUrls: ['./process-loan-application.component.css'],
  imports: [CommonModule, FormsModule, AngularToastifyModule],
})
export class ProcessLoanApplicationComponent implements OnInit {
  loanApplication: any;
  loanPlan: any;
  rejectionReason: string = '';
  showRejectionReason: boolean = false;
  supportingDocumentUrl: string = '';

  constructor(
    private loanService: LoanService,
    private route: ActivatedRoute,
    private toastService: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    //retriving value of route parameter named id from url
    const id = this.route.snapshot.paramMap.get('id');
    this.loanService.fnFindApplicationById(Number(id)).subscribe((data) => {
      this.loanApplication = data;
      this.fetchLoanPlanDetails(this.loanApplication.loanPlanId);
      this.fetchSupportingDocumentUrl(this.loanApplication.applicationId);
    });
  }

  fetchLoanPlanDetails(loanPlanId: number): void {
    this.loanService.fnFindLoanPlanById(loanPlanId).subscribe((data) => {
      this.loanPlan = data;
    });
  }

  fetchSupportingDocumentUrl(applicationId: number): void {
    this.supportingDocumentUrl = `http://localhost:5001/api/loanapplications/supportingDocuments/${applicationId}`;
  }

  downloadSupportingDocument(): void {
    this.loanService
      .fnGetSupportingDocument(this.loanApplication.applicationId)
      .subscribe(
        (response) => {
          const url = window.URL.createObjectURL(response);
          const a = document.createElement('a');
          a.href = url;
          // a.download = 'supportingDocument.pdf';
          a.download = `${this.loanApplication.applicantName}.pdf`;
          a.click();
          window.URL.revokeObjectURL(url);
          this.toastService.info('Document downloaded.');
        },
        (error) => {
          // console.error('Failed to download supporting document:', error);
          // alert('Failed to download supporting document.');
          this.toastService.info('Failed to download supporting document.');
        }
      );
  }

  approveApplication(): void {
    if (this.loanApplication.applicationStatus === 'Rejected') {
      // alert('Cannot approve a rejected application.');
      this.toastService.error('Cannot approve a rejected application.');
      return;
    }
    this.loanApplication.applicationStatus = 'Approved';
    this.loanService.fnUpdateApplication(this.loanApplication).subscribe(
      (response) => {
        // console.log('Approve response:', response);
        this.toastService.success('Loan application approved successfully.');
        setTimeout(() => {
          this.router.navigateByUrl('/app');
        }, 2000);
        // alert('Loan application approved successfully.');
      },
      (error) => {
        // console.error('Failed to approve loan application:', error);
        if (error.status === 200) {
          // alert('Loan application approved successfully.');
          this.toastService.success('Loan application approved successfully.');
          setTimeout(() => {
            this.router.navigateByUrl('/app');
          }, 2000);
        } else {
          // alert('Failed to approve loan application.');
          this.toastService.error('Failed to approve loan application.');
        }
      }
    );
  }

  rejectApplication(): void {
    if (this.loanApplication.applicationStatus === 'Approved') {
      // alert('Cannot reject an approved application.');
      this.toastService.error('Cannot reject an approved application.');

      return;
    }
    this.showRejectionReason = true;
  }

  submitRejection(): void {
    if (!this.rejectionReason.trim()) {
      // alert('Rejection reason is required.');
      this.toastService.error('Rejection reason is required.');
      return;
    }
    this.loanApplication.applicationStatus = 'Rejected';
    this.loanApplication.rejectionReason = this.rejectionReason;
    this.loanService.fnUpdateApplication(this.loanApplication).subscribe(
      (response) => {
        // console.log('Reject response:', response);
        // alert('Loan application rejected successfully.');
        this.toastService.success('Loan application rejected successfully.');
        setTimeout(() => {
          this.router.navigateByUrl('/app');
        }, 2000);
      },
      (error) => {
        // console.error('Failed to reject loan application:', error);
        if (error.status === 200) {
          // alert('Loan application rejected successfully.');
          this.toastService.success('Loan application rejected successfully.');
          setTimeout(() => {
            this.router.navigateByUrl('/app');
          }, 2000);
        } else {
          // alert('Failed to reject loan application.');
          this.toastService.error('Failed to reject loan application.');
        }
      }
    );
  }
}
