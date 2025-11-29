package com.trustify.trustify.controller.Customer;

import com.trustify.trustify.dto.Req.ReviewDto;
import com.trustify.trustify.dto.Res.ResReviewDto;
import com.trustify.trustify.entity.Company;
import com.trustify.trustify.entity.Review;
import com.trustify.trustify.service.Customer.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<ResReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        Review review = reviewService.createReview(reviewDto);
        ResReviewDto resReviewDto = new ResReviewDto();
        resReviewDto.setTitle(review.getTitle());
        resReviewDto.setDescription(review.getDescription());
        resReviewDto.setRating(review.getRating());
        resReviewDto.setNameUser(review.getUser().getName());
        resReviewDto.setNameCompany(review.getCompany().getName());
        resReviewDto.setExpDate(review.getExpDate().toString());
        return ResponseEntity.ok(resReviewDto);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Map<String, Object>> getCompaniesByIndustry(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {
            Map<String, Object> response = new HashMap<>();
        try {
            Page<Review> reviewPage = reviewService.getReviewsByCompanyId(id, page, size); // List<Review>
            response.put("success", true);
            response.put("reviews", reviewPage.getContent()); // List<Review>
            response.put("currentPage", reviewPage.getNumber());
            response.put("totalPages", reviewPage.getTotalPages());
            response.put("totalItems", reviewPage.getTotalElements());
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            log.error("Error getting reviews by id", e);
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
