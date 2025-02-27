package com.cognizant.loan_application.controller;

import com.cognizant.loan_application.entities.LoanPlan;
import com.cognizant.loan_application.service.LoanPlanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loanplans")
public class LoanPlanController {

    @Autowired
    private LoanPlanService loanPlanService;

    @GetMapping
    public List<LoanPlan> getAllLoanPlans() {
        return loanPlanService.getAllLoanPlans();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanPlan> getLoanPlanById(@PathVariable Integer id) {
        Optional<LoanPlan> loanPlan = loanPlanService.getLoanPlanById(id);
        return loanPlan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public LoanPlan createLoanPlan(@RequestBody LoanPlan loanPlan) {
        return loanPlanService.createLoanPlan(loanPlan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanPlan> updateLoanPlan(@PathVariable Integer id, @RequestBody LoanPlan loanPlanDetails) {
        LoanPlan updatedLoanPlan = loanPlanService.updateLoanPlan(id, loanPlanDetails);
        return ResponseEntity.ok(updatedLoanPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanPlan(@PathVariable Integer id) {
        loanPlanService.deleteLoanPlan(id);
        return ResponseEntity.noContent().build();
    }
}
