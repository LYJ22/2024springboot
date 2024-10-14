package com.sample.spring.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.spring.model.ReviewEntity;

public interface ReviewRepositoryCustom{
	Slice<ReviewEntity> findSliceByFoodId(Long foodId, Pageable page);
}
