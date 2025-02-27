package com.cognizant.loan_application.repository;

import java.util.List;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.loan_application.entities.LoanApplication;

@Repository
public interface LoanApplicationsRepository extends JpaRepository<LoanApplication, Integer> {

	List<LoanApplication> findByApplicantEmail(String applicantEmail);
//	List<LoanApplication> findByApplicantPAN(String applicantPAN);

}
