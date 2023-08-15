package com.app.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.UserRequest;
import com.app.response.ApiResponse;
import com.app.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	@SuppressWarnings("rawtypes")
	public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRequest user) throws Exception {
		return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/finduser/{id}")
	public ResponseEntity<ApiResponse> findUser(@PathVariable String id) {
		return new ResponseEntity<>(userService.findUser(id), HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/updateuser", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> updateUser(@RequestBody UserRequest req) throws Exception {
		return new ResponseEntity<>(userService.updateUser(req), HttpStatus.OK);
	}

}
