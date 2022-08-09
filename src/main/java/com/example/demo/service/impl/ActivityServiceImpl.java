package com.example.demo.service.impl;

import com.example.demo.repository.ActivityRepository;
import com.example.demo.entity.Activity;
import com.example.demo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("activityService")
@Transactional
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepo;

    @Override
    public Iterable<Activity> findAll(Long card) {
        return activityRepo.findByCard(card);
    }
}
