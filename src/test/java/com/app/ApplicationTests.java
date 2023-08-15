package com.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import com.app.model.UserDetails;
import com.app.model.UserRequest;
import com.app.repository.UserRepository;
import com.app.response.ApiResponse;
import com.app.service.UserService;
import com.app.service.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ContextConfiguration(classes = { Application.class })
@PropertySource("classpath:/application.properties")
@Slf4j
class ApplicationTests {

	@InjectMocks
	private UserService service = new UserServiceImpl();

	@Mock
	private UserRepository repo;

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void testSaveUser() throws Exception {
		UserRequest req = UserRequest.builder().saIdNumber("100").firstName("srini").lastName("mahankali")
				.address("Johannesburg").mobileNumber("855555555").build();
		UserDetails userDetails = UserDetails.builder().firstName("srini").build();
		when(repo.save(any())).thenReturn(userDetails);
		ApiResponse save = service.saveUser(req);
		Map<String, String> map = (HashMap<String, String>) save.getResponse();
		log.info("" + map.toString());
		assertEquals(map.get("Status"), String.valueOf(HttpStatus.CREATED));
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void testSaveUserWithUserDetails() throws Exception {
		UserRequest req = UserRequest.builder().saIdNumber("100").firstName("srupdate").lastName("mahankali")
				.address("Johannesburg").mobileNumber("855555555").build();
		UserDetails userDetails = UserDetails.builder().firstName("srini").build();
		when(repo.findBySaIdNumberOrMobileNumber(any(), any())).thenReturn(Optional.of(userDetails));
		ApiResponse save = service.saveUser(req);
		Map<String, String> map = (HashMap<String, String>) save.getResponse();
		log.info("" + map.toString());
		assertEquals(map.get("Status"), String.valueOf(HttpStatus.BAD_REQUEST));
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void testSaveUserElse() throws Exception {
		UserRequest req = UserRequest.builder().saIdNumber("100").firstName("srini").lastName("mahankali")
				.address("Johannesburg").mobileNumber("855555555").build();
		when(repo.save(any())).thenReturn(null);
		ApiResponse save = service.saveUser(req);
		Map<String, String> map = (HashMap<String, String>) save.getResponse();
		log.info("" + map.toString());
		assertEquals(map.get("Status"), String.valueOf(HttpStatus.FORBIDDEN));
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void testFindUser() throws Exception {
		UserDetails userDetails = UserDetails.builder().firstName("srini").build();
		Mockito.when(repo.findBySaIdNumberOrMobileNumberOrFirstName(any(), any(), any()))
				.thenReturn(Optional.of(userDetails));
		ApiResponse user = service.findUser("100");
		Map<String, Object> map = (HashMap<String, Object>) user.getResponse();
		log.info("" + map.toString());
		assertEquals(map.get("Status"), String.valueOf(HttpStatus.OK));
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void testFindUserElse() throws Exception {
		Mockito.when(repo.findBySaIdNumberOrMobileNumberOrFirstName(any(), any(), any())).thenReturn(Optional.empty());
		ApiResponse user = service.findUser("srini");
		Map<String, String> map = (HashMap<String, String>) user.getResponse();
		log.info("" + map.toString());
		assertEquals(map.get("Status"), String.valueOf(HttpStatus.BAD_REQUEST));
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void testUpdateUser() throws Exception {
		UserRequest req = UserRequest.builder().saIdNumber("100").firstName("srini").lastName("mahankali")
				.address("Johannesburg").mobileNumber("855555555").build();
		UserDetails userDetails = UserDetails.builder().firstName("srini").build();
		Mockito.when(repo.findBySaIdNumberOrMobileNumber(any(), any())).thenReturn(Optional.of(userDetails));
		when(repo.save(any())).thenReturn(userDetails);
		ApiResponse update = service.updateUser(req);
		Map<String, String> map = (HashMap<String, String>) update.getResponse();
		log.info("" + map.toString());
		assertEquals(map.get("Status"), String.valueOf(HttpStatus.OK));
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void testUpdateUserElse() throws Exception {
		UserRequest req = UserRequest.builder().saIdNumber("100").firstName("srini").lastName("mahankali")
				.address("Johannesburg").mobileNumber("855555555").build();
		UserDetails userDetails = UserDetails.builder().firstName("srini").build();
		Mockito.when(repo.findBySaIdNumberOrMobileNumber(any(), any())).thenReturn(Optional.of(userDetails));
		when(repo.save(any())).thenReturn(null);
		ApiResponse update = service.updateUser(req);
		Map<String, String> map = (HashMap<String, String>) update.getResponse();
		log.info("" + map.toString());
		assertEquals(map.get("Status"), String.valueOf(HttpStatus.BAD_REQUEST));
	}
}
