package com.sample.spring.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//요즘은 Setter 대신 Change를 사용하는 경우가 많다.
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member {
	//primary key 잡는 방법. 변수명이 id라서 id가 아님. memberId여도 id 태그.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=100)
	private String name;
	@Column(name = "uemail")
	private String email;
	private LocalDate createDate;
}
