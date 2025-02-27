package com.cognizant.loan_application.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MyUser {
	@Id
	private String username;
	private String password;
	@Pattern(regexp = "CUSTOMER|BANKMANAGER", message = "Roles must be either CUSTOMER or BANKMANAGER")
	private String roles;
}
