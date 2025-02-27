package com.cognizant.loan_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.loan_application.entities.LoanPlan;

@Repository
public interface LoanPlanRepository extends JpaRepository<LoanPlan, Integer> {
}