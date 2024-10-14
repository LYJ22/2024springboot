package com.sample.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.spring.api.request.CreateReviewRequest;
import com.sample.spring.service.ReviewService;
import com.sample.spring.service.dto.ReviewDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ReviewApi {
	@Autowired
	private ReviewService reviewservice;
	
	@PostMapping("/review")
	public ResponseEntity<String> createReview(@RequestBody CreateReviewRequest request) {
		reviewservice.createReview(
				request.getFoodId(), 
				request.getContent(),
				request.getScore());
		
		return ResponseEntity.status(HttpStatus.CREATED).body("success");
	}
	
	@DeleteMapping("/review/{reviewId}")
	public void deleteReview(
			@PathVariable("reviewId") Long reviewId
			) {
		reviewservice.DeleteReview(reviewId);
	}
	
	@GetMapping("/food/{foodId}/reviews")
	public ReviewDto getFoodReviews(
			@PathVariable("foodId") Long foodId,
			@RequestParam("offset") Integer offset,
			@RequestParam("limit") Integer limit
			) {
		return reviewservice.getFoodReview(foodId, PageRequest.of(offset/limit, limit));
	}
	
}
