package com.example.demo.service;

import com.example.demo.entity.Activity;

public interface ActivityService {
    public Iterable<Activity> findAll(Long card);
}
