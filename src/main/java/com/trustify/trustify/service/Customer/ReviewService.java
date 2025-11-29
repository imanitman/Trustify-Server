package com.trustify.trustify.service.Customer;

import com.trustify.trustify.dto.Req.ReviewDto;
import com.trustify.trustify.entity.Company;
import com.trustify.trustify.entity.Review;
import com.trustify.trustify.entity.User;
import com.trustify.trustify.repository.Customer.ReviewRepository;
import com.trustify.trustify.service.Business.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final CompanyService companyService;

    public Review createReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setDescription(reviewDto.getDescription());
        review.setRating(reviewDto.getRating());
        review.setExpDate(reviewDto.getExpDate());
        User user = userService.findByEmail(reviewDto.getEmail());
        review.setUser(user);
        review.setCompany(companyService.findCompanyByName(reviewDto.getCompanyName()));
        return reviewRepository.save(review);
    }

    public Page<Review> getReviewsByCompanyId(Long companyId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findByCompanyId(companyId, pageable);
    }
}
