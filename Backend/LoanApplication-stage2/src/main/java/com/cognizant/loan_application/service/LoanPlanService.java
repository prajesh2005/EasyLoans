package com.cognizant.loan_application.service;

import com.cognizant.loan_application.entities.LoanPlan;
import com.cognizant.loan_application.repository.LoanPlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanPlanService {

    @Autowired
    private LoanPlanRepository loanPlanRepository;

    public List<LoanPlan> getAllLoanPlans() {
        return loanPlanRepository.findAll();
    }

    public Optional<LoanPlan> getLoanPlanById(Integer id) {
        return loanPlanRepository.findById(id);
    }

    public LoanPlan createLoanPlan(LoanPlan loanPlan) {
        return loanPlanRepository.save(loanPlan);
    }

    public LoanPlan updateLoanPlan(Integer id, LoanPlan loanPlanDetails) {
        LoanPlan loanPlan = loanPlanRepository.findById(id).orElseThrow(() -> new RuntimeException("LoanPlan not found"));
        loanPlan.setLoanPlanType(loanPlanDetails.getLoanPlanType());
        loanPlan.setLoanPlanName(loanPlanDetails.getLoanPlanName());
        return loanPlanRepository.save(loanPlan);
    }

    public void deleteLoanPlan(Integer id) {
        LoanPlan loanPlan = loanPlanRepository.findById(id).orElseThrow(() -> new RuntimeException("LoanPlan not found"));
        loanPlanRepository.delete(loanPlan);
    }
}
