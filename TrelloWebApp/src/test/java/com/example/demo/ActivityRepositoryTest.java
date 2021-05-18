package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.Activity.ActivityReposity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ActivityRepositoryTest {
	@Autowired
	ActivityReposity activityReposity;
	
}
