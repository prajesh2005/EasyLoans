
package com.cognizant.loan_application.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.loan_application.dto.GetLoanApplicationDTO;
import com.cognizant.loan_application.dto.LoanApplicationDTO;
import com.cognizant.loan_application.dto.LoanApplicationDetailsDTO;
import com.cognizant.loan_application.dto.LoanApplicationResponseDTO;
import com.cognizant.loan_application.entities.LoanApplication;
import com.cognizant.loan_application.exception.LoanApplicationCreationException;
import com.cognizant.loan_application.exception.LoanApplicationNotFoundException;
import com.cognizant.loan_application.exception.MaximumRequestLimitReachedException;
import com.cognizant.loan_application.repository.LoanApplicationsRepository;
import com.cognizant.loan_application.repository.LoanPlanRepository;

import jakarta.transaction.Transactional;

@Service
public class LoanApplicationsService {

	@Autowired
	private LoanApplicationsRepository loanApplicationsRepository;

	@Autowired
	private LoanPlanRepository loanPlanRepository;

	private static final Logger logger = LoggerFactory.getLogger(LoanApplicationsService.class);

	private static final String UPLOAD_DIR = "uploads/";

	public List<GetLoanApplicationDTO> getAllNewLoanApplications() {
		logger.info("Fetching all new loan applications");
		List<LoanApplication> loanApplications = loanApplicationsRepository.findAll();
		List<GetLoanApplicationDTO> loanAppDTOs = new ArrayList<>();

		for (LoanApplication loanApp : loanApplications) {
			if ("New".equalsIgnoreCase(loanApp.getApplicationStatus())) {
				GetLoanApplicationDTO dto = new GetLoanApplicationDTO(loanApp.getApplicationId(),
						loanApp.getApplicantName(), loanApp.getApplicantAddress(), loanApp.getApplicantProfession(),
						loanApp.getApplicantEmail(), loanApp.getApplicantPhone(), loanApp.getApplicantPAN(),
						loanApp.getMonthlyIncome(), loanApp.getNoOfDependents(), loanApp.getApplicationDate(),
						loanApp.getApplicationStatus(), loanApp.getLoanPlanId());
				loanAppDTOs.add(dto);
			}
		}

		if (loanAppDTOs.isEmpty()) {
			logger.warn("No new loan applications found");
			throw new LoanApplicationNotFoundException("There are no New Applications...");
		}

		logger.info("Fetched {} new loan applications", loanAppDTOs.size());
		return loanAppDTOs;
	}

	public LoanApplicationDetailsDTO getLoanApplicationById(Integer id) {
		logger.info("Fetching loan application by ID: {}", id);

		LoanApplication loanApplication = loanApplicationsRepository.findById(id)
				.orElseThrow(() -> new LoanApplicationNotFoundException("Loan Application not found"));
		logger.info("Fetched loan application: {}", loanApplication);

		return new LoanApplicationDetailsDTO(loanApplication.getApplicationId(), loanApplication.getApplicantName(),
				loanApplication.getApplicantAddress(), loanApplication.getApplicantProfession(),
				loanApplication.getApplicantEmail(), loanApplication.getApplicantPhone(),
				loanApplication.getApplicantPAN(), loanApplication.getLoanPlanId(), loanApplication.getMonthlyIncome(),
				loanApplication.getNoOfDependents(), loanApplication.getApplicationDate(),
				loanApplication.getApplicationReviewedOn(), loanApplication.getApplicationStatus(),
				loanApplication.getSupportingDocumentsPath());
	}

	@Transactional
	public LoanApplication createNewApplication(LoanApplicationDTO loanApplicationDTO,
			MultipartFile supportingDocuments) {
		logger.info("Creating new loan application for email: {}", loanApplicationDTO.getApplicantEmail());

		List<LoanApplication> existingApplications = loanApplicationsRepository
				.findByApplicantEmail(loanApplicationDTO.getApplicantEmail());
		long newApplicationsCount = existingApplications.stream()
				.filter(app -> "New".equalsIgnoreCase(app.getApplicationStatus())).count();

		if (newApplicationsCount >= 2) {
			logger.warn("Maximum of 2 new loan applications allowed for email: {}",
					loanApplicationDTO.getApplicantEmail());
			throw new MaximumRequestLimitReachedException("Maximum of 2 new loan applications allowed.");
		}

		boolean sameLoanPlanExists = existingApplications.stream()
				.anyMatch(app -> "New".equalsIgnoreCase(app.getApplicationStatus()) && app.getLoanPlanId() != null
						&& app.getLoanPlanId().equals(loanApplicationDTO.getLoanPlanId()));

		if (sameLoanPlanExists) {
			logger.warn("Each new loan application must be for a different loan plan for email: {}",
					loanApplicationDTO.getApplicantEmail());
			throw new MaximumRequestLimitReachedException(
					"Each new loan application must be for a different loan plan.");
		}

		loanPlanRepository.findById(loanApplicationDTO.getLoanPlanId())
				.orElseThrow(() -> new LoanApplicationNotFoundException("Loan plan not found"));

		LoanApplication loanApp = new LoanApplication();
		loanApp.setApplicantName(loanApplicationDTO.getApplicantName());
		loanApp.setApplicantAddress(loanApplicationDTO.getApplicantAddress());
		loanApp.setApplicantProfession(loanApplicationDTO.getApplicantProfession());
		loanApp.setApplicantPhone(loanApplicationDTO.getApplicantPhone());
		loanApp.setApplicantEmail(loanApplicationDTO.getApplicantEmail());
		loanApp.setApplicantPAN(loanApplicationDTO.getApplicantPAN());
		loanApp.setMonthlyIncome(loanApplicationDTO.getMonthlyIncome());
		loanApp.setNoOfDependents(loanApplicationDTO.getNoOfDependents());
		loanApp.setApplicationDate(loanApplicationDTO.getApplicationDate());
		loanApp.setLoanPlanId(loanApplicationDTO.getLoanPlanId());

		loanApp.setApplicationStatus("New");
		loanApp.setApplicationDate(new Date());
		loanApp.setApplicationReviewedOn(new Date());

		if (supportingDocuments != null && !supportingDocuments.isEmpty()) {
			try {
				String fileName = supportingDocuments.getOriginalFilename();
				Path filePath = Paths.get(UPLOAD_DIR, fileName);
				Files.createDirectories(filePath.getParent());
				Files.write(filePath, supportingDocuments.getBytes());
				loanApp.setSupportingDocumentsPath(filePath.toString());
			} catch (IOException e) {
				logger.error("Error saving supporting document: {}", e.getMessage());
				throw new LoanApplicationCreationException("Error saving supporting document: " + e.getMessage());
			}
		}

		try {
			LoanApplication savedLoanApp = loanApplicationsRepository.save(loanApp);
			logger.info("Created new loan application: {}", savedLoanApp);
			return savedLoanApp;
		} catch (Exception e) {
			logger.error("Error creating loan application: {}", e.getMessage());
			throw new LoanApplicationCreationException("Error creating loan application: " + e.getMessage());
		}
	}

//	using pan 
//	@Transactional
//	public LoanApplication createNewApplication(LoanApplicationDTO loanApplicationDTO, MultipartFile supportingDocuments) {
//	    logger.info("Creating new loan application for email: {}", loanApplicationDTO.getApplicantEmail());
//
//	    List<LoanApplication> existingApplications = loanApplicationsRepository
//	            .findByApplicantPAN(loanApplicationDTO.getApplicantPAN());
//	    long newApplicationsCount = existingApplications.stream()
//	            .filter(app -> "New".equalsIgnoreCase(app.getApplicationStatus())).count();
//
//	    if (newApplicationsCount >= 2) {
//	        logger.warn("Maximum of 2 new loan applications allowed for a person: {}",
//	                loanApplicationDTO.getApplicantPAN());
//	        throw new MaximumRequestLimitReachedException("Maximum of 2 new loan applications allowed.");
//	    }
//
//	    boolean sameLoanPlanExists = existingApplications.stream()
//	            .anyMatch(app -> "New".equalsIgnoreCase(app.getApplicationStatus()) && app.getLoanPlanId() != null
//	                    && app.getLoanPlanId().equals(loanApplicationDTO.getLoanPlanId()));
//
//	    if (sameLoanPlanExists) {
//	        logger.warn("Each new loan application must be for a different loan plan for same PAN number: {}",
//	                loanApplicationDTO.getApplicantPAN());
//	        throw new MaximumRequestLimitReachedException(
//	                "Each new loan application must be for a different loan plan.");
//	    }
//
//	    loanPlanRepository.findById(loanApplicationDTO.getLoanPlanId())
//	            .orElseThrow(() -> new LoanApplicationNotFoundException("Loan plan not found"));
//
//	    LoanApplication loanApp = new LoanApplication();
//	    loanApp.setApplicantName(loanApplicationDTO.getApplicantName());
//	    loanApp.setApplicantAddress(loanApplicationDTO.getApplicantAddress());
//	    loanApp.setApplicantProfession(loanApplicationDTO.getApplicantProfession());
//	    loanApp.setApplicantPhone(loanApplicationDTO.getApplicantPhone());
//	    loanApp.setApplicantEmail(loanApplicationDTO.getApplicantEmail());
//	    loanApp.setApplicantPAN(loanApplicationDTO.getApplicantPAN());
//	    loanApp.setMonthlyIncome(loanApplicationDTO.getMonthlyIncome());
//	    loanApp.setNoOfDependents(loanApplicationDTO.getNoOfDependents());
//	    loanApp.setApplicationDate(loanApplicationDTO.getApplicationDate());
//	    loanApp.setLoanPlanId(loanApplicationDTO.getLoanPlanId());
//
//	    loanApp.setApplicationStatus("New");
//	    loanApp.setApplicationDate(new Date());
//	    loanApp.setApplicationReviewedOn(new Date());
//
//	  
//	    if (supportingDocuments != null && !supportingDocuments.isEmpty()) {
//	        try {
//	            String fileName = supportingDocuments.getOriginalFilename();
//	            Path filePath = Paths.get(UPLOAD_DIR, fileName);
//	            Files.createDirectories(filePath.getParent());
//	            Files.write(filePath, supportingDocuments.getBytes());
//	            loanApp.setSupportingDocumentsPath(filePath.toString());
//	        } catch (IOException e) {
//	            logger.error("Error saving supporting document: {}", e.getMessage());
//	            throw new LoanApplicationCreationException("Error saving supporting document: " + e.getMessage());
//	        }
//	    }
//
//	    try {
//	        LoanApplication savedLoanApp = loanApplicationsRepository.save(loanApp);
//	        logger.info("Created new loan application: {}", savedLoanApp);
//	        return savedLoanApp;
//	    } catch (Exception e) {
//	        logger.error("Error creating loan application: {}", e.getMessage());
//	        throw new LoanApplicationCreationException("Error creating loan application: " + e.getMessage());
//	    }
//	}

	public void updateLoanApplicationStatus(int applicationId, LoanApplicationResponseDTO status) {
		logger.info("Updating loan application status for ID: {}", applicationId);

		LoanApplication loanApplication = loanApplicationsRepository.findById(applicationId).orElseThrow(() -> {
			logger.warn("Loan Application not found for ID: {}", applicationId);
			return new LoanApplicationNotFoundException("Loan Application not found");
		});

		loanApplication.setApplicationStatus(status.getApplicationStatus());
		loanApplication.setApplicationReviewedOn(new Date());
		loanApplicationsRepository.save(loanApplication);
		logger.info("Updated loan application status for ID: {}", applicationId);

	}

	public LoanApplication getLoanApplicationEntityById(Integer id) {
		return loanApplicationsRepository.findById(id)
				.orElseThrow(() -> new LoanApplicationNotFoundException("Loan Application not found"));
	}

}
