
package com.app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.app.model.UserDetails;
import com.app.model.UserRequest;
import com.app.repository.UserRepository;
import com.app.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;

	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ApiResponse saveUser(UserRequest request) throws Exception {
		ApiResponse response = new ApiResponse();
		Map<String, String> responseMap = new HashMap<>();
		Optional<UserDetails> details = repo.findBySaIdNumberOrMobileNumber(request.getSaIdNumber(),
				request.getMobileNumber());
		if (details.isPresent()) {
			responseMap.put("Message", "User already register,please use your login credentials.");
			responseMap.put("Status", String.valueOf(HttpStatus.BAD_REQUEST));
			response.setResponse(responseMap);
			return response;
		}
		try {
			if (isValid(request)) {
				UserDetails userDetails = modelMapper().map(request, UserDetails.class);
				userDetails.setCreatedDate(new Date());
				userDetails.setUpdatedDate(new Date());
				UserDetails saveUser = repo.save(userDetails);
				if (!ObjectUtils.isEmpty(saveUser)) {
					responseMap.put("Message", "User created successfully.");
					responseMap.put("Status", String.valueOf(HttpStatus.CREATED));
					response.setResponse(responseMap);
					return response;
				} else {
					responseMap.put("Message", "Failed to create user.");
					responseMap.put("Status", String.valueOf(HttpStatus.FORBIDDEN));
					response.setResponse(responseMap);
					return response;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return response;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ApiResponse updateUser(UserRequest req) throws Exception {

		ApiResponse response = new ApiResponse();
		Map<String, String> responseMap = new HashMap<>();

		Optional<UserDetails> details = repo.findBySaIdNumberOrMobileNumber(req.getSaIdNumber(), req.getMobileNumber());

		try {
			if (details.isPresent()) {
				UserDetails userDetails = details.get();
				userDetails.setFirstName(req.getFirstName());
				userDetails.setLastName(req.getLastName());
				userDetails.setMobileNumber(req.getMobileNumber());
				userDetails.setAddress(req.getAddress());
				userDetails.setSaIdNumber(req.getSaIdNumber());
				userDetails.setUpdatedDate(new Date());
				UserDetails savedDetails = repo.save(userDetails);
				if (!ObjectUtils.isEmpty(savedDetails)) {
					responseMap.put("Message", "User updated successfully.");
					responseMap.put("Status", String.valueOf(HttpStatus.OK));
					response.setResponse(responseMap);
					return response;
				}
				else {
					responseMap.put("Message", "Failed to update user.");
					responseMap.put("Status", String.valueOf(HttpStatus.BAD_REQUEST));
					response.setResponse(responseMap);
					return response;
				}
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return response;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ApiResponse findUser(String sid) {

		Optional<UserDetails> users = repo.findBySaIdNumberOrMobileNumberOrFirstName(sid, sid, sid);
		ApiResponse response = new ApiResponse();
		Map<String, Object> responseMap = new HashMap<>();
		if (users.isPresent()) {
			responseMap.put("Status", String.valueOf(HttpStatus.OK));
			responseMap.put("userDetails", users.get());
			response.setResponse(responseMap);
		} else {
			responseMap.put("Message", "please enter valid deatails");
			responseMap.put("Status", String.valueOf(HttpStatus.BAD_REQUEST));
			response.setResponse(responseMap);
			return response;
		}
		return response;

	}

	private boolean isValid(UserRequest request) throws Exception {
		boolean flag = false;
		try {
			if (StringUtils.hasText(request.getFirstName())) {
				flag = true;
			}
			if (StringUtils.hasText(request.getLastName())) {
				flag = true;
			}
			if (request.getSaIdNumber() != null) {
				flag = true;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return flag;
	}
}
