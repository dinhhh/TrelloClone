package com.example.demo.Activity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Card.Card;

@Service("activityService")
@Transactional
public class ActivityServiceImpl implements ActivityService{
	@Autowired
	private ActivityReposity activityRepo;

	@Override
	public Iterable<Activity> findAll(Card card) {
		return activityRepo.findByCard(card);
	}
}
