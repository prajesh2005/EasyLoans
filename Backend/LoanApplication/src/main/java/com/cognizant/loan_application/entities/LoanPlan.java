package com.cognizant.loan_application.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
@Table(name = "LoanPlans")
public class LoanPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_plan_id", length = 10)
    private Integer loanPlanId;

    @Column(name = "loan_plan_type", length = 20)
    private String loanPlanType;

    @Column(name = "loan_plan_name", length = 50)
    private String loanPlanName;
}
