package com.sample.spring.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sample.spring.model.QReviewEntity;
import com.sample.spring.model.ReviewEntity;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

	@Autowired
	private JPAQueryFactory queryFactory;
	
	@Override
	public Slice<ReviewEntity> findSliceByFoodId(Long foodId, Pageable page) {
		List<ReviewEntity> reviews = queryFactory.select(QReviewEntity.reviewEntity)
				.from(QReviewEntity.reviewEntity)
				.where(QReviewEntity.reviewEntity.foodId.eq(foodId))
				.offset((long) page.getPageNumber() * page.getPageSize()) // 0 * 10 pagesize = 0~9 사이 선택
				.limit(page.getPageSize()+1)	// pagesize limit
				.fetch();
		

		
		return new SliceImpl<>(reviews.stream().limit(page.getPageSize()).toList(),
				page,
				reviews.size() > page.getPageSize());
	}

}
