package com.sample.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.spring.entity.TestEntity;
import com.sample.spring.service.TestService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class TestApi {
	@Autowired
	private TestService testService;
	
	@GetMapping("/test/create")
	public void createTest() {
		testService.create("lyj", 10);
	}
	
	@PostMapping("/test/create")
	public void postCreate(
			@RequestBody CreateTestRequest request
			
			) {
		log.info(request.getName());
		System.out.println("/--------------"+request.getName()+"--------------/");
		testService.create(request.getName(), request.getAge());
	}
	
	@PutMapping("/test/update") // update?id=	name,age는 requestbody
	public void putUpdate(
			@RequestParam("id") Long id,
			@RequestBody CreateTestRequest request
			) {
		testService.update(id, request.getName(), request.getAge());
	}
	
	@DeleteMapping("/test/{id}/delete")
	public void deleteTest(
			@PathVariable("id") Long id
			) {
		testService.delete(id);
	}
	
	@GetMapping("/test")
	public ResponseEntity<List<TestEntity>> jsonData(){
		List<TestEntity> data = testService.findAll();
		return ResponseEntity.ok(data);	//200 ok만 들어온다. 400 500 오류를 뱉지 않음
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	public static class CreateTestRequest{
		private String name;
		private Integer age;
	}
}
