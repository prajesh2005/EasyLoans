package com.cognizant.loan_application.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.io.UrlResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.loan_application.dto.GetLoanApplicationDTO;
import com.cognizant.loan_application.dto.LoanApplicationDTO;
import com.cognizant.loan_application.dto.LoanApplicationDetailsDTO;
import com.cognizant.loan_application.dto.LoanApplicationResponseDTO;
import com.cognizant.loan_application.entities.LoanApplication;
import com.cognizant.loan_application.service.LoanApplicationsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loanapplications")
@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = {"*"})
public class LoanApplicationsController {

	@Autowired
	private LoanApplicationsService loanApplicationService;

	@GetMapping
	@PreAuthorize("hasAuthority('BANKMANAGER')")
	public ResponseEntity<List<GetLoanApplicationDTO>> getAllNewLoanApplications(Authentication authentication) {
		List<GetLoanApplicationDTO> loanApplications = loanApplicationService.getAllNewLoanApplications();
		return ResponseEntity.ok(loanApplications);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('BANKMANAGER')")
	public ResponseEntity<LoanApplicationDetailsDTO> getLoanApplicationById(Authentication authentication, @Valid @PathVariable Integer id) {
		LoanApplicationDetailsDTO loanApplication = loanApplicationService.getLoanApplicationById(id);
		return ResponseEntity.ok(loanApplication);
	}
    
	@PostMapping
	@PreAuthorize("hasAuthority('CUSTOMER')")
	public ResponseEntity<String> createLoanApplication(Authentication authentication, @RequestParam("loanApplicationDTO") String loanApplicationDTOString, @RequestParam("supportingDocuments") MultipartFile supportingDocuments) {
	    try {
	        String contentType = supportingDocuments.getContentType();
	        List<String> allowedTypes = Arrays.asList(
	            "application/pdf", "image/jpeg", "image/png"
	        );

	        if (!allowedTypes.contains(contentType)) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type. Only PDF, JPEG, and PNG are allowed.");
	        }

	        ObjectMapper objectMapper = new ObjectMapper();
	        LoanApplicationDTO loanApplicationDTO = objectMapper.readValue(loanApplicationDTOString, LoanApplicationDTO.class);
	        loanApplicationService.createNewApplication(loanApplicationDTO, supportingDocuments);
	        return ResponseEntity.ok("Loan application submitted successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Error processing loan application: " + e.getMessage());
	    }
	}


	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('BANKMANAGER')")
	public ResponseEntity<String> updateLoanApplicationStatus(Authentication authentication, @PathVariable int id,
			@Valid @RequestBody LoanApplicationResponseDTO status) {
		loanApplicationService.updateLoanApplicationStatus(id, status);
		return new ResponseEntity<>("Status updated successfully to " + status.getApplicationStatus(), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('BANKMANAGER')")
    @GetMapping("/supportingDocuments/{applicationId}")
    public ResponseEntity<Resource> getSupportingDocument(@PathVariable Integer applicationId) throws MalformedURLException {
        LoanApplication loanApplication = loanApplicationService.getLoanApplicationEntityById(applicationId);
        Path filePath = Paths.get(loanApplication.getSupportingDocumentsPath());	//constructing file path from doc
        Resource resource = new UrlResource(filePath.toUri());		//creating url resource from file path, resource representing the file to be downloaded

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
