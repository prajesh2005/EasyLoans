package com.cognizant.loan_application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.loan_application.dto.DocumentTypeDTO;
import com.cognizant.loan_application.service.DocumentTypesService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/documenttypes")
@CrossOrigin(origins = {"*"})
public class DocumentTypesController {

	@Autowired
	private DocumentTypesService documentTypeService;

	@GetMapping
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@SecurityRequirement(name = "Bearer Authentication")
	public ResponseEntity<List<DocumentTypeDTO>> getAllDocumentTypes(Authentication authentication) {
		List<DocumentTypeDTO> documents = documentTypeService.getAllDocumentTypes();
		return ResponseEntity.ok(documents);

	}

}
