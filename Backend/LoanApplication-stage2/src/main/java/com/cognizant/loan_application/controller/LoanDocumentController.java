package com.cognizant.loan_application.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.loan_application.service.LoanDocumentService;

@RestController
@RequestMapping("/api/loandocuments")
public class LoanDocumentController {

    @Autowired
    private LoanDocumentService loanDocumentService;

//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("supportingDocuments") String base64File, @RequestParam("loanApplicationId") Integer loanApplicationId) {
//        String filePath = loanDocumentService.saveBase64File(base64File, loanApplicationId);
//        return new ResponseEntity<>(filePath, HttpStatus.OK);
//    }
}
