package com.app.service;

import com.app.model.UserRequest;
import com.app.response.ApiResponse;

public interface UserService {

	@SuppressWarnings("rawtypes")
	public ApiResponse saveUser(UserRequest request) throws Exception;

	@SuppressWarnings("rawtypes")
	public ApiResponse findUser(String id);

	@SuppressWarnings("rawtypes")
	public ApiResponse updateUser(UserRequest req) throws Exception;

}
