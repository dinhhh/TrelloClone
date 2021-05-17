package com.example.demo.Activity;

import com.example.demo.Card.Card;

public interface ActivityService {
	public Iterable<Activity> findAll(Card card);
}
