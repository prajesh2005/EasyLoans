import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { LoanService } from '../services/loan.service';
import { response } from 'express';

@Component({
  selector: 'app-get-document-types',
  imports: [CommonModule],
  templateUrl: './get-document-types.component.html',
  styleUrl: './get-document-types.component.css',
})
export class GetDocumentTypesComponent {
  documentTypes: any;

  constructor(private service: LoanService) {}

  ngOnInit(): void {
    this.getAllDocumentTypes();
  }

  getAllDocumentTypes(): void {
    this.service.fnRetriveDocumentTypes().subscribe((response) => {
      this.documentTypes = response;
    });
  }
}
