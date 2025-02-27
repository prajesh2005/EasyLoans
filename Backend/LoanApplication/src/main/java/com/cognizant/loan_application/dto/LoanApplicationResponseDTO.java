package com.cognizant.loan_application.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanApplicationResponseDTO {
	@Pattern(regexp = "Approved|Rejected", message = "Application status should be either Approved or Rejected")
    private String applicationStatus;
}
