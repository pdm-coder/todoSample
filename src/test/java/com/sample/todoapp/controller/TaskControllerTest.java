package com.sample.todoapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sample.todoapp.TodoappApplication;
import com.sample.todoapp.model.EnumStatus;
import com.sample.todoapp.model.Task;
import com.sample.todoapp.model.User;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@SpringBootTest(classes = { TodoappApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

	@LocalServerPort
	private int port;

	static JSONObject taskJSON;

	
	@BeforeEach
	public void testAddTask() {

		OkHttpClient client = new OkHttpClient();

		JSONObject userJSON = null;
		Long userId;
		Response response = null;
		try {
			userJSON = testAddUser();
			userId = userJSON.getLong("id");

			Task task = new Task();
			task.setId(1);
			task.setName("Reading books");
			task.setStatus(EnumStatus.TODO);
			task.setDate(LocalDate.now());

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			
			String json = userJSON.toString();
			User user = objectMapper.readValue(json, User.class);

			task.setUser(user);

			String jsonFormattedValues = null;
			jsonFormattedValues = objectMapper.writeValueAsString(task);

			final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

			RequestBody body = RequestBody.create(JSON, jsonFormattedValues);
			Request request = new Request.Builder().url("http://localhost:" + port + "/todoapp/user/" + userId + "/task")
					.addHeader("User-Agent", "OkHttp Bot").post(body).build();

			response = client.newCall(request).execute();
			taskJSON = new JSONObject(response.body().string());
		} catch (JSONException e2) {
			e2.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.body().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		assertEquals(200, response.code());
	}

	@Test
	public void testGetAllTaskByUser() {
		OkHttpClient client = new OkHttpClient();

		JSONObject userJSON = null;
		Long userId;
		userJSON = testAddUser();
		Response response = null;
		try {
			userId = userJSON.getLong("id");

			Request request = new Request.Builder().url("http://localhost:" + port + "/todoapp/user/" + userId + "/task/all")
					.get().build();

			response = client.newCall(request).execute();
			JSONArray resultArray = new JSONArray(response.body().string());
			assertEquals(1, resultArray.length());

		} catch (JSONException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.body().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testUpdateTaskStatus() {

		OkHttpClient client = new OkHttpClient();

		Response response = null;
		String resultStatus = null;
		String taskStatus = null;
		try {
			taskStatus = String.valueOf(taskJSON.get("status"));

			assertEquals("TODO", taskStatus);

			taskJSON.put("status", "INPROGRESS");

			final MediaType mediaTypeJSON = MediaType.parse("application/json; charset=utf-8");

			RequestBody body = RequestBody.create(mediaTypeJSON, taskJSON.toString());
			Request request = new Request.Builder().url("http://localhost:" + port + "/todoapp/task")
					.addHeader("User-Agent", "OkHttp Bot").put(body).build();

			response = client.newCall(request).execute();

			JSONObject updateResultJSON = new JSONObject(response.body().string());
			resultStatus = String.valueOf(updateResultJSON.get("status"));
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.body().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		assertEquals("INPROGRESS", resultStatus);
	}
	
	
	@AfterEach
	public void testDeleteTask() {
		OkHttpClient client = new OkHttpClient();

		Long taskId;
		Response response = null;
		try {
			taskId = taskJSON.getLong("id");

			Request request = new Request.Builder().url("http://localhost:" + port + "/todoapp/task/" + taskId).delete()
					.build();

			response = client.newCall(request).execute();

			assertEquals(200, response.code());

		} catch (JSONException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.body().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private JSONObject testAddUser() {

		OkHttpClient client = new OkHttpClient();

		User user = new User();
		user.setId(1);
		user.setName("John");

		String jsonFormattedValues = null;
		try {
			jsonFormattedValues = new ObjectMapper().writeValueAsString(user);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		RequestBody body = RequestBody.create(JSON, jsonFormattedValues);
		Request request = new Request.Builder().url("http://localhost:" + port + "/todoapp/user/add")
				.addHeader("User-Agent", "OkHttp Bot").post(body).build();

		Response response = null;
		JSONObject userJSON = null;
		try {
			response = client.newCall(request).execute();
			userJSON = new JSONObject(response.body().string());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.body().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		assertEquals(200, response.code());

		return userJSON;
	}


}
