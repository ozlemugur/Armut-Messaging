package com.armut;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.armut.model.UserEntity;
import com.armut.request.BlockUserRequest;
import com.armut.request.LoginRequest;
import com.armut.request.Message;
import com.armut.request.SendMessageRequest;
import com.armut.request.SignUpRequest;

import lombok.extern.slf4j.Slf4j;
@Slf4j
class ArmutMessagingServiceApplicationTests extends AbstractTest {


	@Test
	void contextLoads() {

	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void sendMessageUserBlocked() throws Exception {

		{
			String uri = "/users/signup";
			SignUpRequest request = new SignUpRequest();
			request.setUserName("zoro");
			request.setPassword("lola");
			String inputJson = super.mapToJson(request);
			MvcResult mvcResult = mvc.perform(
					MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
					.andReturn();
			int status = mvcResult.getResponse().getStatus();
			log.error("ARMUT SIGNUP  zoro is created ********** " + status);
		

		}

		{
			String uri = "/users/signup";
			SignUpRequest request = new SignUpRequest();
			request.setUserName("hawkins");
			request.setPassword("lola");
			String inputJson = super.mapToJson(request);
			MvcResult mvcResult = mvc.perform(
					MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
					.andReturn();
			int status = mvcResult.getResponse().getStatus();
			log.error("ARMUT SIGNUP  hawkins is created ********** " + status);
			

		}

		{
			String uri = "/users/login";
			LoginRequest request = new LoginRequest("zoro", "lola");
			String inputJson = super.mapToJson(request);
			MvcResult mvcResult = mvc.perform(
					MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
					.andReturn();

			int status = mvcResult.getResponse().getStatus();
			log.error("ARMUT LOGIN   zoro********** " + status);
	
		}
		
		{
			String uri = "/users/login";
			LoginRequest request = new LoginRequest("hawkins", "lola");
			String inputJson = super.mapToJson(request);
			MvcResult mvcResult = mvc.perform(
					MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
					.andReturn();

			int status = mvcResult.getResponse().getStatus();
			log.error("ARMUT LOGIN  hawkins  ********** " + status);
		
		}
		
		{
			String uri = "/users/block";
			String userName = "zoro";
			UserEntity user = userService.getUserByUserName(userName);

			BlockUserRequest request = new BlockUserRequest(userName, "hawkins", user.getToken());
			String inputJson = super.mapToJson(request);
			MvcResult mvcResult = mvc.perform(
					MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
					.andReturn();

			int status = mvcResult.getResponse().getStatus();
			log.error("ARMUT BLOCK zoro bloked hawkins ********** " + status);
	
		}

		String uri = "/messages/sendmessage";
		SendMessageRequest request = new SendMessageRequest();
		String userName = "hawkins";
		UserEntity user = userService.getUserByUserName(userName);
		request.setToken(user.getToken());
		request.setUserName(userName);
		request.setReceiverUserName("zoro");
		Message message = new Message(userName, "zoro", "tadam ...");
		request.setMessage(message);
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		log.error("ARMUT SEND MESSAGE  hawkins to zoro ********** " + status);
		assertEquals(404, status);
	}


}
