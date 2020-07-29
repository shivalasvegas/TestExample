package com.qa.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qa.demo.exceptions.CardNotFoundException;
import com.qa.demo.persistence.domain.Card;
import com.qa.demo.persistence.repo.CardRepo;

@Service
public class CardService {

	private CardRepo repo;

	public CardService(CardRepo repo) {
		super();
		this.repo = repo;
	}

	public Card create(Card card) {
		Card saved = this.repo.save(card);
		return saved;
	}

	public Card read(Long id) {
		Card found = this.repo.findById(id).orElseThrow(CardNotFoundException::new);
		return found;
	}

	public List<Card> readAll() {
		List<Card> found = this.repo.findAll();
		return found;
	}

	public Card update(Card newCard, Long id) {
		Card found = read(id);

		found.setColour(newCard.getColour());
		found.setCost(newCard.getCost());
		found.setName(newCard.getName());

		Card saved = this.repo.save(found);
		return saved;
	}

	public boolean delete(Long id) {
		this.repo.deleteById(id);
		boolean deleted = !this.repo.existsById(id);
		return deleted;
	}

}
