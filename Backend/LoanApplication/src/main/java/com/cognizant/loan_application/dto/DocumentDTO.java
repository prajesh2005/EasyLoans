package com.cognizant.loan_application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentDTO {
    private String documentURL;
    private Integer documentTypeId;
}
