package com.armut.test;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.armut.common.ResponseEnum;
import com.armut.common.Utils;
import com.armut.model.UserEntity;
import com.armut.request.BlockUserRequest;
import com.armut.request.LoginRequest;
import com.armut.request.SignUpRequest;
import com.armut.response.BlockUserResponse;
import com.armut.response.SignUpResponse;
import com.armut.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTest extends AbstractTest {

	@Autowired
	private Utils utils;

	@Autowired
	private UserService userService;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	@Order(1)
	public void signUpMockData1() throws Exception {
		String uri = "/users/signup";

		SignUpRequest request = new SignUpRequest();
		request.setUserName("luffy");
		request.setPassword("lola");
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		log.error("ARMUT SIGNUP mock data, luffy is  created. ********** " + status);
		
	}

	@Test
	@Order(2)
	public void signUpMockData2() throws Exception {
		String uri = "/users/signup";

		SignUpRequest request = new SignUpRequest();
		request.setUserName("robin");
		request.setPassword("lola");
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		log.error("ARMUT SIGNUP mocck data , robin is created.  ********** " + status);
	}
	
	@Test
	@Order(3)
	public void signUpMockData3() throws Exception {
		String uri = "/users/signup";

		SignUpRequest request = new SignUpRequest();
		request.setUserName("blackbeard");
		request.setPassword("lola");
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		log.error("ARMUT SIGNUP mock data , blackbeard is created.  ********** " + status);
	}

	@Test
	public void signUp() throws Exception {
		String uri = "/users/signup";
		Random random = new Random(4000);

		SignUpRequest request = new SignUpRequest();
		request.setUserName(random.toString());
		request.setPassword("lola");
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		log.error("ARMUT SIGNUP  ********** " + status);
		assertEquals(200, status);
	}

	@Test
	public void signUpWithAUserNameAlreadyExists() throws Exception {
		String uri = "/users/signup";
		Random random = new Random(4000);

		SignUpRequest request = new SignUpRequest();
		request.setUserName("sondia");
		request.setPassword("pppp");
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		MvcResult mvcResult2 = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult2.getResponse().getStatus();
		log.error("ARMUT SIGNUP WITH UserNameAlreadyExists ********** " + status);
		assertEquals(404, status);

		String content = mvcResult2.getResponse().getContentAsString();
		SignUpResponse response = mapFromJson(content, SignUpResponse.class);
		assertEquals(response.getCode(), ResponseEnum.USERNAME_ALREADY_EXISTS.getCode());
		log.error("ARMUT SIGNUP WITH UserNameAlreadyExists ********** " + response.getMessage());

	}

	@Test
	@Order(4)
	public void login() throws Exception {

		String uri = "/users/login";
		LoginRequest request = new LoginRequest("robin", "lola");
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		log.error("ARMUT LOGIN  ********** " + status);
		assertEquals(200, status);

	}

	@Test
	@Order(5)
	public void blockUser() throws Exception {

		String uri = "/users/block";
		String userName = "robin";
		UserEntity user = userService.getUserByUserName(userName);

		BlockUserRequest request = new BlockUserRequest(userName, "blackbeard", user.getToken());
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		log.error("ARMUT BLOCK  ********** " + status);
		assertEquals(200, status);

	}

	@Test
	public void blockUserWithWrongUserName() throws Exception {

		String uri = "/users/block";
		String userName = "xxxxxxxxx";

		BlockUserRequest request = new BlockUserRequest(userName, "kimsinsen", "xxxxxx");
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		log.error("ARMUT BLOCK  ********** " + status);
		assertEquals(401, status);

		String content = mvcResult.getResponse().getContentAsString();
		BlockUserResponse response = mapFromJson(content, BlockUserResponse.class);
		assertEquals(response.getCode(), ResponseEnum.CHECK_YOUR_DATA.getCode());
		log.error("ARMUT BLOCK USER WITH WRONG NAME ********** " + response.getMessage());
	}

	@Test
	public void blockUserBlankRequest() throws Exception {
		String uri = "/users/block";
		BlockUserRequest request = new BlockUserRequest();
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		log.error("ARMUT BLOCK  ********** " + status);
		assertEquals(401, status);

		String content = mvcResult.getResponse().getContentAsString();
		BlockUserResponse response = mapFromJson(content, BlockUserResponse.class);
		assertEquals(response.getCode(), ResponseEnum.CHECK_YOUR_DATA.getCode());
		log.error("ARMUT BLOCK USER BLANK REQUEST********** " + response.getMessage());
	}

}
