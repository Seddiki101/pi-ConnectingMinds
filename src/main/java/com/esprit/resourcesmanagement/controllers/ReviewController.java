package com.esprit.resourcesmanagement.controllers;

import com.esprit.resourcesmanagement.entities.Review;
import com.esprit.resourcesmanagement.services.ResourceService;
import com.esprit.resourcesmanagement.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller of Review operations.
 */
@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/all-reviews")
    @ResponseBody
    public List<Review> getAll() {
        return this.reviewService.getAllReviews();
    }
    @GetMapping("/all-reviewsByResource/{resourceId}")
    @ResponseBody
    public List<Review> getAllByResource(@PathVariable Long resourceId) {
        return this.reviewService.getAllReviewsByResource(resourceId);
    }

    @GetMapping("/getReviewById/{id}")
    @ResponseBody
    public Review getReviewById(@PathVariable Long id) {
        return id != null ? this.reviewService.findReviewById(id) : null;
    }

    @PutMapping("/updateReview/{id}")
    @ResponseBody
    public Review updateReview(@RequestBody Review review,@PathVariable Long id) {
        return review != null ? this.reviewService.updateReview(review,id) : null;
    }

    @PostMapping("/addReview")
    @ResponseBody
    public Review addReview(@RequestBody Review review) {
        this.resourceService.ReviewsUp(review.getResource().getResourceId());
        return reviewService.addReview(review);
    }

    @DeleteMapping("/deleteReview/{id}")
    public void deleteReview(@PathVariable Long id) {
        Review review =this.reviewService.findReviewById(id);
        this.resourceService.ReviewsDown(review.getResource().getResourceId());
        reviewService.deleteReview(id);
    }

}
