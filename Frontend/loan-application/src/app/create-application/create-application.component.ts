import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoanService } from '../services/loan.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { ToastService, AngularToastifyModule } from 'angular-toastify';

@Component({
  selector: 'app-create-application',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    AngularToastifyModule,
    AngularToastifyModule,
  ],
  templateUrl: './create-application.component.html',
  styleUrls: ['./create-application.component.css'],
  providers: [DatePipe],
})
export class CreateApplicationComponent implements OnInit {
  newLoanApplicationForm!: FormGroup; //!...non null assertion, indicating that this property will be initialized and won't be null or undefined at runtime
  newLoanApplicationResponse: any;
  minDate: string;
  maxDate: string;
  selectedFile: File | null = null;
  showCustomProfessionField = false;

  constructor(
    private formBuilder: FormBuilder,
    private service: LoanService,
    private datePipe: DatePipe,
    private toastService: ToastService
  ) {
    const today = new Date();
    this.minDate = this.datePipe.transform(today, 'yyyy-MM-dd') || '';
    // this.maxDate = this.datePipe.transform(new Date(today.getFullYear() + 1, today.getMonth(), today.getDate()), 'yyyy-MM-dd') || '';
    // this.maxDate = this.datePipe.transform(today, 'yyyy-MM-dd') || '';
    this.maxDate = this.minDate;
  }

  ngOnInit() {
    // creating a FormGroup instance, collection of FormControl object
    this.newLoanApplicationForm = this.formBuilder.group({
      applicantName: [
        '',
        [
          Validators.required,
          Validators.pattern('^[a-zA-Z\\s]+$'),
          Validators.minLength(10),
        ],
      ],
      applicantAddress: ['', Validators.required],
      applicantProfession: ['', Validators.required],
      customProfession: [''],
      applicantEmail: ['', [Validators.required, Validators.email]],
      applicantPhone: [
        '',
        [Validators.required, Validators.pattern('^[0-9]{10}$')],
      ],
      applicantPAN: [
        '',
        [Validators.required, Validators.pattern('[A-Z]{5}[0-9]{4}[A-Z]{1}')],
      ],
      monthlyIncome: [0, [Validators.required, Validators.min(0)]],
      applicationDate: [this.minDate, Validators.required],
      loanPlanId: ['', Validators.required],
      noOfDependents: [0, [Validators.required, Validators.min(0)]],
      supportingDocuments: [null, Validators.required],
    });
  }

  onFileChange(event: any) {
    const file = event.target.files[0]; //to access the first file selected by the user
    const allowedTypes = ['application/pdf', 'image/jpeg', 'image/png'];

    if (file && allowedTypes.includes(file.type)) {
      this.selectedFile = file;
      this.newLoanApplicationForm.patchValue({
        supportingDocuments: file,
      });
      this.newLoanApplicationForm
        .get('supportingDocuments')
        ?.updateValueAndValidity();
    } else {
      this.toastService.error(
        'Invalid file type. Only PDF, JPEG and PNG are allowed.'
      );
      event.target.value = ''; //clearing the file input
    }
  }

  onProfessionChange(event: any) {
    const selectedProfession = event.target.value;
    this.showCustomProfessionField = selectedProfession === 'Other';
    if (this.showCustomProfessionField) {
      this.newLoanApplicationForm.controls['customProfession'].setValidators([
        Validators.required,
      ]);
    } else {
      this.newLoanApplicationForm.controls[
        'customProfession'
      ].clearValidators();
    }
    this.newLoanApplicationForm.controls[
      'customProfession'
    ].updateValueAndValidity();
  }

  createLoanApplication() {
    if (this.newLoanApplicationForm.valid) {
      // using formData object, bcoz it allows to send both JSON data and file in single request
      const formData = new FormData();
      const loanApplicationData = { ...this.newLoanApplicationForm.value }; //... -> spread operator, clones the original object

      // if profession is Other, use the custom profession value
      if (loanApplicationData.applicantProfession === 'Other') {
        loanApplicationData.applicantProfession =
          loanApplicationData.customProfession;
      }

      delete loanApplicationData.customProfession;
      delete loanApplicationData.supportingDocuments;

      formData.append(
        'loanApplicationDTO',
        JSON.stringify(loanApplicationData)
      );
      if (this.selectedFile) {
        formData.append('supportingDocuments', this.selectedFile);
      }

      // here we are sending formdata object to backend, backend will check the business rules, if everything is ok, will accept the application, else application will be rejected, giving a particular error that happened
      this.service.fnAddApplication(formData).subscribe(
        (response) => {
          this.newLoanApplicationResponse = {
            success: true,
            message: response,
          };
          // alert('Application submitted successfully');
          this.toastService.info('Application submitted successfully');
          this.newLoanApplicationForm.reset();
          this.selectedFile = null;
          const fileInput = document.getElementById(
            'supportingDocuments'
          ) as HTMLInputElement;
          if (fileInput) {
            fileInput.value = '';
          }
          setTimeout(() => {
            this.newLoanApplicationResponse = null;
          }, 2000);
        },
        (error) => {
          console.error('Error response:', error);
          const errorMessage =
            error.error ||
            'An error occurred while submitting the application.';
          this.newLoanApplicationResponse = {
            success: false,
            message: errorMessage,
          };
        }
      );
    }
  }
}
