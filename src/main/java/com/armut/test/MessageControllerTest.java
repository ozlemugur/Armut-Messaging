package com.armut.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.armut.common.Utils;
import com.armut.model.UserEntity;
import com.armut.request.GetMessagesRequest;
import com.armut.request.Message;
import com.armut.request.SendMessageRequest;
import com.armut.request.SignUpRequest;
import com.armut.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageControllerTest extends AbstractTest {

	@Autowired
	private Utils utils;

	@Autowired
	private UserService userService;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	/**
	 * Introdcution
	 * 
	 * <dl>
	 * <dt><span class="strong">Heading 1</span></dt>
	 * <dd>There is a line break.</dd>
	 * <dt><span class="strong">Heading 2</span></dt>
	 * <dd>There is a line break.</dd>
	 * </dl>
	 *
	 * @param x foo
	 * @return foo
	 * @throws foo
	 */
	@Test
	public void sendMessage() throws Exception {
		{
			String uri = "/users/signup";

			SignUpRequest request = new SignUpRequest();
			request.setUserName("fujitora");
			request.setPassword("lola");
			String inputJson = super.mapToJson(request);
			MvcResult mvcResult = mvc.perform(
					MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
					.andReturn();

			int status = mvcResult.getResponse().getStatus();
			log.info("ARMUT SIGNUP  fujitora is created. ********** " + status);
			assertEquals(200, status);

		}
		
		{
			String uri = "/users/signup";
			SignUpRequest request = new SignUpRequest();
			request.setUserName("garp");
			request.setPassword("lola");
			String inputJson = super.mapToJson(request);
			MvcResult mvcResult = mvc.perform(
					MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
					.andReturn();
			int status = mvcResult.getResponse().getStatus();
			log.info("ARMUT SIGNUP   garp is created ********** " + status);
			assertEquals(200, status);

		}
		String uri = "/messages/sendmessage";

		SendMessageRequest request = new SendMessageRequest();
		String userName = "robin";
		UserEntity user = userService.getUserByUserName(userName);
		request.setToken(user.getToken());
		request.setUserName(userName);
		request.setReceiverUserName("luffy");
		Message message = new Message(userName, "luffy", "mugiwara");
		request.setMessage(message);
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

//		String content = mvcResult.getResponse().getContentAsString();
//		SendMessageResponse response = super.mapFromJson(content, SendMessageResponse.class);
//		
//		log.error("ARMUT SEND MESSAGE ********** " + response.getMessage());
//		

		int status = mvcResult.getResponse().getStatus();
		log.error("ARMUT SEND MESSAGE  ********** " + status);
		assertEquals(200, status);

	}

	@Test
	public void sendMessageNonUser() throws Exception {

		String uri = "/messages/sendmessage";

		SendMessageRequest request = new SendMessageRequest();
		String userName = "robin";
		UserEntity user = userService.getUserByUserName(userName);
		request.setToken(user.getToken());
		request.setUserName(userName);
		request.setReceiverUserName("kl");
		Message message = new Message(userName, "kl", "non user test");
		request.setMessage(message);
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		log.error("ARMUT SEND MESSAGE  NONE USER ********** " + status);
		assertEquals(200, status);

	}

	/**
	 * Introdcution
	 * 
	 * <dl>
	 * <dt><span class="strong">Heading 1</span></dt>
	 * <dd>There is a line break.</dd>
	 * <dt><span class="strong">Heading 2</span></dt>
	 * <dd>There is a line break.</dd>
	 * </dl>
	 *
	 * @param x foo
	 * @return foo
	 * @throws foo
	 */
	@Test
	public void getMessage() throws Exception {
		String uri = "/messages/getmessages";
		String userName = "robin";
		UserEntity user = userService.getUserByUserName(userName);

		GetMessagesRequest request = new GetMessagesRequest();
		request.setToken(user.getToken());
		request.setUserName(userName);
		String inputJson = super.mapToJson(request);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		log.error("ARMUT GET MESSAGES ********** " + status);
		assertEquals(200, status);

		log.error("ARMUT GET MESSAGES ********** " + content);

	}

}
