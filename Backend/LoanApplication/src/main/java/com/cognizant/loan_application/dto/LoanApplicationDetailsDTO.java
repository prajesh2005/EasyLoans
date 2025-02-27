package com.cognizant.loan_application.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanApplicationDetailsDTO {
	private Integer applicationId;
    private String applicantName;
    private String applicantAddress;
    private String applicantProfession;
    private String applicantEmail;
    private String applicantPhone;
    private String applicantPAN;
	private Integer loanPlanId;
    private Integer monthlyIncome;
    private Integer noOfDependents;
    private Date applicationDate;
    private Date applicationReviewedOn;
    private String applicationStatus;
    private String supportingDocumentsPath;
}
