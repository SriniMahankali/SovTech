package com.app.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

	@NotEmpty(message = "first name must not be empty")
	private String firstName;

	@NotEmpty(message = "last name must not be empty")
	private String lastName;

	@NotEmpty(message = "id number must not be empty")
	private String saIdNumber;

	private String mobileNumber;
	
	private String address;
	
}
