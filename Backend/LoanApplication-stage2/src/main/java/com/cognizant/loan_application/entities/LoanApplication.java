package com.cognizant.loan_application.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
@Table(name = "LoanApplications")
public class LoanApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "application_id", length = 10)
	private Integer applicationId;

	@Column(name = "applicant_name", length = 30)
	private String applicantName;

	@Column(name = "applicant_address", length = 100)
	private String applicantAddress;

	@Column(name = "applicant_profession", length = 20)
	private String applicantProfession;

	@Column(name = "applicant_phone")
	private String applicantPhone;

	@Column(name = "applicant_email", length = 100)
	private String applicantEmail;

	@Column(name = "applicant_pan", length = 10)
	private String applicantPAN;

	@Column(name = "monthly_income", length = 10)
	private Integer monthlyIncome;

	@Column(name = "no_of_dependents", length = 10)
	private Integer noOfDependents;

	@Temporal(TemporalType.DATE)
	@Column(name = "application_date")
	private Date applicationDate;

	@Column(name = "loan_plan_id", length = 10)
//    @ManyToOne
//    @JoinColumn(name = "loan_plan_id", referencedColumnName = "loan_plan_id")
	private Integer loanPlanId;

	@Column(name = "application_status", length = 20)
	private String applicationStatus;

	@Column(name = "application_reviewed_on", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date applicationReviewedOn = new Date();
	
//    private String supportingDocumentsPath; 
    @Column(name = "supporting_documents_path")
    private String supportingDocumentsPath; 

}