package com.okta.springbootspa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okta.springbootspa.controller.UserController;
import com.okta.springbootspa.controller.UserOrderController;
import com.okta.springbootspa.model.User;
import com.okta.springbootspa.model.UserOrder;
import com.okta.springbootspa.model.UserStock;
import com.okta.springbootspa.repository.UserOrderRepository;
import com.okta.springbootspa.repository.UserRepository;
import com.okta.springbootspa.repository.UserStockRepository;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RequiredArgsConstructor
@SpringBootTest
@AutoConfigureMockMvc
class SpringBootSpaApplicationTests {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserController userController;
	@Autowired
	private UserOrderController userOrderController;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserOrderRepository userOrderRepository;
	@Autowired
	private UserStockRepository userStockRepository;

	@BeforeEach
	public void setUp(){
		RestAssuredMockMvc.standaloneSetup(this.userController, this.userOrderController);
	}


	@Test
	void GetUser() {
		RestAssuredMockMvc.given().accept(ContentType.JSON).when()
				.get("/users")
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	void GetUserByID(){
		RestAssuredMockMvc.given().accept(ContentType.JSON).when()
				.get("/teste/{id}", 66)
				.then()
				.statusCode(HttpStatus.OK.value());
	}


	@Test
	void GetOrder(){
		RestAssuredMockMvc.given().accept(ContentType.JSON).when()
				.get("/orders")
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	void postUser() throws Exception {
		User user = new User("julio", "qualquercoisa", 10000000D);
		String token = "Bearer eyJraWQiOiJuQktld1lTTHB6YU5RaW9pRFJoQ2xRcUJtQ0V0OVRROXUzbU1zMm1LZkpRIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULl92bDBLV0Mtdjd6UkNnZ2VNQ0xjekszVGw0bTltVU5Kcll0b1NyektST3ciLCJpc3MiOiJodHRwczovL2Rldi0zNDE2MjE0Lm9rdGEuY29tL29hdXRoMi9kZWZhdWx0IiwiYXVkIjoiYXBpOi8vZGVmYXVsdCIsImlhdCI6MTY0Nzg4NDQ2NSwiZXhwIjoxNjQ3ODg4MDY1LCJjaWQiOiIwb2Ezang1cXpiZk1ub1lMYzVkNyIsInVpZCI6IjAwdTQ3NDJla3o4SlFJZGh1NWQ3Iiwic2NwIjpbImVtYWlsIiwib3BlbmlkIiwicHJvZmlsZSJdLCJhdXRoX3RpbWUiOjE2NDc4ODQ0NjEsInN1YiI6InZpdG9yaWFzbDMxMDdAZ21haWwuY29tIn0.V_759aVsnH5S9PYX_xExrGfdXqVKtL-QMOj_sHtHq1F_jikI4d4cWu_zo1h8za7RLqpt_RAJ7yUvJM0F2KIvP3CD8Re7fv8uQEv-Znox4W9BIHACmxdkWTQP1lkz6jiVIB29zBJ-ttDQp_Dr2ljcAQ-YsARf7nbkFiqoq7fVgLuwVMiBjqDMA6apurWdDSHrBxsS06FFtPFUt2nuozGFj90CPd4U4QwY00hnxz1QDZvXwwBvg42lxmExS17EJzwmznlG2sbmCr68EscT_xoyd2aoiIq5mp_xvb1K7YIDCpyW7PfqOQUKwhZRlmvCVB-5TJxV519ZqWgvlAVJ3Fbg8g";

			mockMvc.perform(post("/users").header(HttpHeaders.AUTHORIZATION, token).contentType("application/json")
					.content(objectMapper
					.writeValueAsString(user))).andExpect(status().isOk());

		Optional<User> PostUser = userRepository.findById(76L);
		Assertions.assertThat(PostUser.get().getUsername()).isEqualTo("julio");
	}

	@Test
	void getOrder() throws Exception {
		Optional<UserOrder> order = userOrderRepository.findById(327L);
		String token = "Bearer eyJraWQiOiJuQktld1lTTHB6YU5RaW9pRFJoQ2xRcUJtQ0V0OVRROXUzbU1zMm1LZkpRIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULl92bDBLV0Mtdjd6UkNnZ2VNQ0xjekszVGw0bTltVU5Kcll0b1NyektST3ciLCJpc3MiOiJodHRwczovL2Rldi0zNDE2MjE0Lm9rdGEuY29tL29hdXRoMi9kZWZhdWx0IiwiYXVkIjoiYXBpOi8vZGVmYXVsdCIsImlhdCI6MTY0Nzg4NDQ2NSwiZXhwIjoxNjQ3ODg4MDY1LCJjaWQiOiIwb2Ezang1cXpiZk1ub1lMYzVkNyIsInVpZCI6IjAwdTQ3NDJla3o4SlFJZGh1NWQ3Iiwic2NwIjpbImVtYWlsIiwib3BlbmlkIiwicHJvZmlsZSJdLCJhdXRoX3RpbWUiOjE2NDc4ODQ0NjEsInN1YiI6InZpdG9yaWFzbDMxMDdAZ21haWwuY29tIn0.V_759aVsnH5S9PYX_xExrGfdXqVKtL-QMOj_sHtHq1F_jikI4d4cWu_zo1h8za7RLqpt_RAJ7yUvJM0F2KIvP3CD8Re7fv8uQEv-Znox4W9BIHACmxdkWTQP1lkz6jiVIB29zBJ-ttDQp_Dr2ljcAQ-YsARf7nbkFiqoq7fVgLuwVMiBjqDMA6apurWdDSHrBxsS06FFtPFUt2nuozGFj90CPd4U4QwY00hnxz1QDZvXwwBvg42lxmExS17EJzwmznlG2sbmCr68EscT_xoyd2aoiIq5mp_xvb1K7YIDCpyW7PfqOQUKwhZRlmvCVB-5TJxV519ZqWgvlAVJ3Fbg8g";

		mockMvc.perform(get("/orders").header(HttpHeaders.AUTHORIZATION, token).contentType("application/json")
				.content(objectMapper
						.writeValueAsString(order))).andExpect(status().isOk());

		Optional<UserOrder> getOrderr = userOrderRepository.findById(327L);
		Assertions.assertThat(getOrderr.get().getStockName()).isEqualTo("CREDIT ACCEPDRN");
	}

	@Test
	void getWallet() throws Exception {
		Optional<UserStock> order = userStockRepository.findById(14L);
		String token = "Bearer eyJraWQiOiJuQktld1lTTHB6YU5RaW9pRFJoQ2xRcUJtQ0V0OVRROXUzbU1zMm1LZkpRIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULl92bDBLV0Mtdjd6UkNnZ2VNQ0xjekszVGw0bTltVU5Kcll0b1NyektST3ciLCJpc3MiOiJodHRwczovL2Rldi0zNDE2MjE0Lm9rdGEuY29tL29hdXRoMi9kZWZhdWx0IiwiYXVkIjoiYXBpOi8vZGVmYXVsdCIsImlhdCI6MTY0Nzg4NDQ2NSwiZXhwIjoxNjQ3ODg4MDY1LCJjaWQiOiIwb2Ezang1cXpiZk1ub1lMYzVkNyIsInVpZCI6IjAwdTQ3NDJla3o4SlFJZGh1NWQ3Iiwic2NwIjpbImVtYWlsIiwib3BlbmlkIiwicHJvZmlsZSJdLCJhdXRoX3RpbWUiOjE2NDc4ODQ0NjEsInN1YiI6InZpdG9yaWFzbDMxMDdAZ21haWwuY29tIn0.V_759aVsnH5S9PYX_xExrGfdXqVKtL-QMOj_sHtHq1F_jikI4d4cWu_zo1h8za7RLqpt_RAJ7yUvJM0F2KIvP3CD8Re7fv8uQEv-Znox4W9BIHACmxdkWTQP1lkz6jiVIB29zBJ-ttDQp_Dr2ljcAQ-YsARf7nbkFiqoq7fVgLuwVMiBjqDMA6apurWdDSHrBxsS06FFtPFUt2nuozGFj90CPd4U4QwY00hnxz1QDZvXwwBvg42lxmExS17EJzwmznlG2sbmCr68EscT_xoyd2aoiIq5mp_xvb1K7YIDCpyW7PfqOQUKwhZRlmvCVB-5TJxV519ZqWgvlAVJ3Fbg8g";

		mockMvc.perform(get("/wallet/{id_user}", 66L).header(HttpHeaders.AUTHORIZATION, token).contentType("application/json")
				.content(objectMapper
						.writeValueAsString(order))).andExpect(status().isOk());

		Optional<UserStock> getWallet = userStockRepository.findById(14L);
		Assertions.assertThat(getWallet.get().getId()).isEqualTo(14L);
	}

}
