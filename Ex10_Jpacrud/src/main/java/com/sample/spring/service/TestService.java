package com.sample.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sample.spring.entity.TestEntity;
import com.sample.spring.repository.TestRepository;

@Service
public class TestService {

	@Autowired
	private TestRepository testrepository;
	
	public void create(String name, Integer age) {
		TestEntity testEntity = new TestEntity(name, age);
		testrepository.save(testEntity);
	}
	
	public void update(Long id, String name, Integer age) {
		TestEntity testEntity = testrepository.findById(id).get();
		testEntity.changeNameAndAge(name, age);
		testrepository.save(testEntity);
	}
	
	public void delete(Long id) {
		testrepository.deleteById(id);
	}
	
	public List<TestEntity> findAll(){
		
		return testrepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}
}
