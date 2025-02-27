package com.cognizant.loan_application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cognizant.loan_application.dto.GetLoanApplicationDTO;
import com.cognizant.loan_application.dto.LoanApplicationDTO;
import com.cognizant.loan_application.dto.LoanApplicationDetailsDTO;
import com.cognizant.loan_application.dto.LoanApplicationResponseDTO;
import com.cognizant.loan_application.entities.LoanApplication;
import com.cognizant.loan_application.entities.LoanPlan;
import com.cognizant.loan_application.repository.LoanApplicationsRepository;
import com.cognizant.loan_application.repository.LoanPlanRepository;
import static org.mockito.Mockito.*;

public class LoanApplicationsServiceTest {

	@InjectMocks
	private LoanApplicationsService loanApplicationsService;

	@Mock
	private LoanApplicationsRepository loanApplicationsRepository;

	@Mock
	private LoanPlanRepository loanPlanRepository;

	private LoanApplicationDTO loanApplicationDTO;
	private LoanApplication loanApplication;
	private LoanPlan loanPlan;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		loanApplicationDTO = new LoanApplicationDTO();
		loanApplicationDTO.setApplicantEmail("test@example.com");
		loanApplicationDTO.setLoanPlanId(1);

		loanPlan = new LoanPlan();
		loanPlan.setLoanPlanId(1);
		loanPlan.setLoanPlanName("Home Loan");

		loanApplication = new LoanApplication();
		loanApplication.setApplicationId(1);
		loanApplication.setApplicantEmail("test@example.com");
		loanApplication.setLoanPlanId(1);
		loanApplication.setApplicationStatus("New");
	}

	@Test
	public void testGetAllNewLoanApplications() {
		LoanApplication loanApp1 = new LoanApplication();
		loanApp1.setApplicationStatus("New");
		LoanApplication loanApp2 = new LoanApplication();
		loanApp2.setApplicationStatus("Approved");

		when(loanApplicationsRepository.findAll()).thenReturn(Arrays.asList(loanApp1, loanApp2));

		GetLoanApplicationDTO dto = new GetLoanApplicationDTO();
		dto.setApplicationId(loanApp1.getApplicationId());
		dto.setApplicantName(loanApp1.getApplicantName());
		dto.setApplicantAddress(loanApp1.getApplicantAddress());
		dto.setApplicantProfession(loanApp1.getApplicantProfession());
		dto.setApplicantEmail(loanApp1.getApplicantEmail());
		dto.setApplicantPhone(loanApp1.getApplicantPhone());
		dto.setApplicantPAN(loanApp1.getApplicantPAN());
		dto.setMonthlyIncome(loanApp1.getMonthlyIncome());
		dto.setNoOfDependents(loanApp1.getNoOfDependents());
		dto.setApplicationDate(loanApp1.getApplicationDate());
		dto.setApplicationStatus(loanApp1.getApplicationStatus());
		dto.setLoanPlanId(loanApp1.getLoanPlanId());

		when(loanApplicationsRepository.findAll()).thenReturn(Arrays.asList(loanApp1, loanApp2));

		List<GetLoanApplicationDTO> result = loanApplicationsService.getAllNewLoanApplications();

		assertEquals(1, result.size());
	}

	@Test
	public void testGetLoanApplicationById() {
	    LoanApplication loanApp = new LoanApplication();
	    loanApp.setApplicationId(1);
	    loanApp.setApplicationReviewedOn(new Date()); 

	    when(loanApplicationsRepository.findById(1)).thenReturn(Optional.of(loanApp));

	    LoanApplicationDetailsDTO dto = new LoanApplicationDetailsDTO();
	    dto.setApplicationId(loanApp.getApplicationId());
	    dto.setApplicantName(loanApp.getApplicantName());
	    dto.setApplicantAddress(loanApp.getApplicantAddress());
	    dto.setApplicantProfession(loanApp.getApplicantProfession());
	    dto.setApplicantEmail(loanApp.getApplicantEmail());
	    dto.setApplicantPhone(loanApp.getApplicantPhone());
	    dto.setApplicantPAN(loanApp.getApplicantPAN());
	    dto.setMonthlyIncome(loanApp.getMonthlyIncome());
	    dto.setNoOfDependents(loanApp.getNoOfDependents());
	    dto.setApplicationDate(loanApp.getApplicationDate());
	    dto.setApplicationStatus(loanApp.getApplicationStatus());
	    dto.setApplicationReviewedOn(loanApp.getApplicationReviewedOn()); 

	    LoanApplicationDetailsDTO result = loanApplicationsService.getLoanApplicationById(1);

	    assertEquals(dto, result);
	}

    @Test
    public void testCreateNewApplication() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mocking the dependencies
        LoanPlan loanPlan = new LoanPlan();
        loanPlan.setLoanPlanId(1); // Ensure LoanPlan class has this method or set ID appropriately
        LoanApplicationDTO loanApplicationDTO = new LoanApplicationDTO();
        loanApplicationDTO.setApplicantEmail("test@example.com");
        loanApplicationDTO.setLoanPlanId(1);
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setApplicationStatus("New");

        when(loanPlanRepository.findById(1)).thenReturn(Optional.of(loanPlan));
        when(loanApplicationsRepository.findByApplicantEmail("test@example.com")).thenReturn(Arrays.asList());
        when(loanApplicationsRepository.save(any(LoanApplication.class))).thenReturn(loanApplication);

        // Call the method under test
        LoanApplication result = loanApplicationsService.createNewApplication(loanApplicationDTO, null);

        // Verify the results
        assertNotNull(result);
        assertEquals("New", result.getApplicationStatus());
        verify(loanApplicationsRepository, times(1)).save(any(LoanApplication.class));
    }

	@ParameterizedTest
	@CsvSource({ "Approved", "Rejected" })
	public void testUpdateLoanApplicationStatus(String status) {
		LoanApplication loanApp = new LoanApplication();
		loanApp.setApplicationId(1);
		loanApp.setApplicationStatus("New");

		when(loanApplicationsRepository.findById(1)).thenReturn(Optional.of(loanApp));

		LoanApplicationResponseDTO statusDTO = new LoanApplicationResponseDTO();
		statusDTO.setApplicationStatus(status);

		loanApplicationsService.updateLoanApplicationStatus(1, statusDTO);

		assertEquals(status, loanApp.getApplicationStatus());
	}
}
