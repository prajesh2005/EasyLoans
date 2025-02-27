package com.cognizant.loan_application.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "LoanDocuments")
public class LoanDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "document_URL", length = 200)
	private String documentURL;

	@ManyToOne
	@JoinColumn(name = "document_type_id", nullable = false)
	private DocumentType documentType;

	@ManyToOne
	@JoinColumn(name = "loan_application_id", nullable = false)
	private LoanApplication loanApplication;
}
