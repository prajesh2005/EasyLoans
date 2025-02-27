package com.cognizant.loan_application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cognizant.loan_application.dto.DocumentTypeDTO;
import com.cognizant.loan_application.entities.DocumentType;
import com.cognizant.loan_application.repository.DocumentTypesRepository;

public class DocumentTypesServiceTest {

    @InjectMocks
    private DocumentTypesService documentTypesService;

    @Mock
    private DocumentTypesRepository documentTypesRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDocumentTypes() {
        DocumentType docType1 = new DocumentType(1, "Passport");
        DocumentType docType2 = new DocumentType(2, "Driver's License");

        when(documentTypesRepository.findAll()).thenReturn(Arrays.asList(docType1, docType2));

        List<DocumentTypeDTO> result = documentTypesService.getAllDocumentTypes();

        assertEquals(2, result.size());
        assertEquals("Passport", result.get(0).getDocumentType());
        assertEquals("Driver's License", result.get(1).getDocumentType());
    }
}