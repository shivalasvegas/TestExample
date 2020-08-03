package com.qa.demo.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.demo.exceptions.CardNotFoundException;
import com.qa.demo.persistence.domain.Card;
import com.qa.demo.persistence.repo.CardRepo;
import com.qa.demo.service.CardService;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CardServiceUnitTest {
	
	@MockBean
	CardRepo repo;
	
	@Autowired
	CardService cardService;
	
	
	
	@Test
	void testCreate() {
		Card card = new Card("Gargle Blaster", "purple", "0.00");
		when(repo.save(card)).thenReturn(card);
		assertEquals(cardService.create(card), card);
		verify(repo, Mockito.times(1)).save(card);
	}
	
	@Test
	void testRead() {
		Card card = new Card("Jabberwocky", "Green", "0,00");
		
		when(repo.findById(42L).orElseThrow(CardNotFoundException::new)).thenReturn(card);
		assertEquals(cardService.read(42L), card);
		verify(repo, Mockito.times(1)).findById(42L);
		
	}

}
