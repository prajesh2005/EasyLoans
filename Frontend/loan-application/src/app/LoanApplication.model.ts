export interface LoanApplication {
  applicationId: number;
  applicantName: string;
  applicantAddress: string;
  applicantProfession: string;
  applicantPhone: string;
  applicantEmail: string;
  applicantPAN: string;
  monthlyIncome: number;
  noOfDependents: number;
  applicationDate: Date;
  loanPlanId: number;
  applicationStatus: string;
  applicationReviewedOn: Date;
  loanPlanName?: string;
  rejectionReason?: string;
}
