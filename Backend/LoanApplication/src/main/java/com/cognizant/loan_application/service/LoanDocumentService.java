package com.cognizant.loan_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.loan_application.repository.LoanDocumentRepository;

@Service
public class LoanDocumentService {

    @Autowired
    private LoanDocumentRepository loanDocumentRepository;
}
