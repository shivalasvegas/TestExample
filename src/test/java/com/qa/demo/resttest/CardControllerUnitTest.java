package com.qa.demo.resttest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.demo.persistence.domain.Card;
import com.qa.demo.persistence.repo.CardRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class CardControllerUnitTest {
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private CardRepo repo;
	
	@Autowired
	private ObjectMapper mapper = new ObjectMapper();
	
	private Card testCard;
	private Card newTestCard;
	private Card testCardWithID;
	private long id;
	private List<Card> listCard = new ArrayList<>();

	
	@Before  // Makes sure there is something in the repo
	public void init() {
		this.repo.deleteAll();
		this.testCard = new Card("Gargle blaster", "Green", "Not for sale");
		
		this.testCardWithID = this.repo.save(this.testCard);
		
		this.id = this.testCardWithID.getId();  
		this.testCardWithID.setId(this.id);
	
		this.listCard.add(testCardWithID);
	}
	
	@Test
	public void testCreate() throws JsonProcessingException, Exception {
		MockHttpServletRequestBuilder mockRequest = 
				MockMvcRequestBuilders.request(HttpMethod.POST, "/card/create");
		
		mockRequest.contentType(MediaType.APPLICATION_JSON)
			.content(this.mapper.writeValueAsString(testCardWithID))
			.accept(MediaType.APPLICATION_JSON);
	
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(testCardWithID));
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}
	
	@Test
	public void testRead() throws JsonProcessingException, Exception {
		MockHttpServletRequestBuilder mockRequest = 
				MockMvcRequestBuilders.request(HttpMethod.GET, "/card/get/1");
		
		mockRequest.contentType(MediaType.APPLICATION_JSON)
			.content(this.mapper.writeValueAsString(testCardWithID))
			.accept(MediaType.APPLICATION_JSON);
	
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(testCardWithID));
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}
	
	@Test
	public void testReadAll() throws JsonProcessingException, Exception {
		MockHttpServletRequestBuilder mockRequest = 
				MockMvcRequestBuilders.request(HttpMethod.GET, "/card/getAll");
		
		mockRequest.contentType(MediaType.APPLICATION_JSON)
			.content(this.mapper.writeValueAsString(listCard))
			.accept(MediaType.APPLICATION_JSON);
	
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(listCard));
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}
	
	@Test
	public void testUpdate() throws JsonProcessingException, Exception {
		this.newTestCard = new Card("Universal Translator", "Purple", "Not for sale");
		this.newTestCard.setId(testCardWithID.getId());
	MockHttpServletRequestBuilder mockRequest = 
	MockMvcRequestBuilders.request(HttpMethod.PUT, "/card/update/" + testCardWithID.getId());
		
	mockRequest.contentType(MediaType.APPLICATION_JSON)
	.content(this.mapper.writeValueAsString(newTestCard))
	.accept(MediaType.APPLICATION_JSON);

	ResultMatcher matchStatus = MockMvcResultMatchers.status().isAccepted();
	ResultMatcher matchContent = MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(newTestCard));
	this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}
	
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		MockHttpServletRequestBuilder mockRequest = 
				MockMvcRequestBuilders.request(HttpMethod.DELETE, "/card/remove/" + testCardWithID.getId());
		
		mockRequest.contentType(MediaType.APPLICATION_JSON)
			.content(this.mapper.writeValueAsString(testCardWithID))
			.accept(MediaType.APPLICATION_JSON);
	
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isNoContent();
		this.mock.perform(mockRequest).andExpect(matchStatus);
	}
	

	
	
}
