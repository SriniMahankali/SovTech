package com.app.response;

import lombok.Data;

@Data
public class ApiResponse<T> {
	
	private T response;
	

}
