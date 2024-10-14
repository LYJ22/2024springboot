package com.sample.spring.service;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.sample.spring.model.ReviewEntity;
import com.sample.spring.repository.FoodRepository;
import com.sample.spring.repository.ReviewRepository;
import com.sample.spring.service.dto.ReviewDto;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {
	@Autowired
	private FoodRepository foodrepository;
	@Autowired
	private ReviewRepository reviewrepository;
	
	@Transactional
	public void createReview(Long foodId, String content, Double score) {
		foodrepository.findById(foodId).orElseThrow();
		
		ReviewEntity review = ReviewEntity.builder()
				.foodId(foodId)
				.content(content)
				.score(score)
				.createdAt(ZonedDateTime.now())
				.build();
		
		reviewrepository.save(review);
	}
	
	public void DeleteReview(Long reviewId) {
		ReviewEntity review = reviewrepository.findById(reviewId).orElseThrow();
		reviewrepository.delete(review);
	}
	
	public ReviewDto getFoodReview(Long foodId, Pageable page) {
		Double avgScore = reviewrepository.getAvgScoreByFoodId(foodId);
		
		Slice<ReviewEntity> reviews = reviewrepository.findSliceByFoodId(foodId, page);
		
		return ReviewDto.builder()
				.avgScore(avgScore)
				.reviews(reviews.getContent())
				.page(
						ReviewDto.ReviewDtoPage.builder()
						.offset(page.getPageNumber() * page.getPageSize())
						.limit(page.getPageSize())
						.build()
						)
				.build();
	}
}
