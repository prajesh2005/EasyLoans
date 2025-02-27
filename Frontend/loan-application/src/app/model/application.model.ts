export class Application {
  applicantName: string;
  applicantAddress: string;
  applicantProfession: string;
  applicantEmail: string;
  applicantPhone: string;
  applicantPAN: string;
  monthlyIncome: number;
  applicationDate: string;
  loanPlanId: number;
  noOfDependents: number;

  constructor() {
    this.applicantName = '';
    this.applicantAddress = '';
    this.applicantProfession = '';
    this.applicantEmail = '';
    this.applicantPhone = '';
    this.applicantPAN = '';
    this.monthlyIncome = 0;
    this.applicationDate = '';
    this.loanPlanId = <any>'';
    this.noOfDependents = 0;
  }
}
