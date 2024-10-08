package com.sample.spring;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sample.spring.domain.Member;
import com.sample.spring.repository.MemberRepository;

@SpringBootTest
public class MemberTests {

	@Autowired
	private MemberRepository repo;

	@Test
	public void testInsert() {
		for(int i = 1; i < 101; i++) {
			Member member = Member.builder()
					.name("test"+i)
					.email("Test"+i+"@test.com")
					.createDate(LocalDate.now())
					.build();
			
			repo.save(member);
		}
	}
}
