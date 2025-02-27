package com.cognizant.loan_application.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanApplicationDTO {
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Applicant name must contain only letters and spaces")
	@Size(min = 10, message = "Applicant name must be at least 10 characters long")
	private String applicantName;
	
	private String applicantAddress;
	
	private String applicantProfession;
	
	@Email(message = "Email should be valid")
	private String applicantEmail;

	@Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits long")
	private String applicantPhone;
	
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "PAN must be exactly 10 characters long and in the format ABCDE1234F")
	private String applicantPAN;
	
	private Integer monthlyIncome;
	
	@Min(value = 0, message = "No of dependents cannot be negative")
	private Integer NoOfDependents;
	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date applicationDate;
		
	private Integer loanPlanId;

//    private MultipartFile supportingDocuments; // Add this field
}
