package com.cognizant.loan_application.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.loan_application.dto.DocumentTypeDTO;
import com.cognizant.loan_application.entities.DocumentType;
import com.cognizant.loan_application.repository.DocumentTypesRepository;

@Service
public class DocumentTypesService {
	@Autowired
	private DocumentTypesRepository documentTypesRepository;

	private static final Logger logger = LoggerFactory.getLogger(DocumentTypesService.class);

	public List<DocumentTypeDTO> getAllDocumentTypes()

	{
		logger.info("Fetching all document types");
		List<DocumentType> docTypes = documentTypesRepository.findAll();
		List<DocumentTypeDTO> docTypesDTO = new ArrayList<>();

		for (DocumentType docType : docTypes) {
			DocumentTypeDTO dto = new DocumentTypeDTO(docType.getId(), docType.getDocumentType());
			docTypesDTO.add(dto);
			logger.info("Fetched {} document types", docTypesDTO.size());
		}
		return docTypesDTO;
	}

}
