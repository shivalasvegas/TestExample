package com.qa.demo.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
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
	public void testCreate() {
		Card card = new Card("Gargle Blaster", "purple", "0.00");
		when(repo.save(card)).thenReturn(card);
		assertEquals(cardService.create(card), card);
		verify(repo, Mockito.times(1)).save(card);
	}
	
	@Test
	public void testRead() throws CardNotFoundException{
		Optional<Card> card = Optional.ofNullable(new Card("Jabberwocky", "Green", "0,00"));
		
		when(repo.findById((long) 1)).thenReturn(card);
		
		assertEquals(cardService.read((long) 1), card.get());
		verify(repo, Mockito.times(1)).findById((long) 1);
		
	}
	
	@Test
	public void testReadAll(){
		List<Card> card = new ArrayList<>();
			
		when(repo.findAll()).thenReturn(card);
		assertEquals(cardService.readAll(), card);
		
	}
	
	@Test
	public void testDelete() throws IllegalArgumentException {
		when(repo.existsById((long) 1)).thenReturn(false);
		assertTrue(cardService.delete((long) 1));
		
		when(repo.existsById((long) 1)).thenReturn(true);
		assertFalse(cardService.delete((long) 1));
	}
	
	@Test
	public void testUpdate() throws CardNotFoundException{
		Card newCard = new Card("Gargle Blaster", "purple", "0.00");
		Optional <Card> optionalCard = Optional.ofNullable(newCard);
		
		when(repo.save(newCard)).thenReturn(newCard);
		when(repo.findById((long) 1)).thenReturn(optionalCard);
		
		assertEquals(cardService.update(newCard, (long) 1), newCard);
		verify(repo, Mockito.times(1)).save(newCard);
		verify(repo, Mockito.times(1)).findById((long) 1);
	}
	

}
