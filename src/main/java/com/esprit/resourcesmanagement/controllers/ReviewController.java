package com.esprit.resourcesmanagement.controllers;

import com.esprit.resourcesmanagement.entities.Review;
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

    @GetMapping("/all-reviews")
    @ResponseBody
    public List<Review> getAll() {
        return this.reviewService.getAllReviews();
    }

    @GetMapping("/getReviewById/{id}")
    @ResponseBody
    public Review getReviewById(@PathVariable Long id) {
        return id != null ? this.reviewService.findReviewById(id) : null;
    }

    @PutMapping("/updateReview")
    @ResponseBody
    public Review updateReview(@RequestBody Review review) {
        return review != null ? this.reviewService.updateReview(review) : null;
    }

    @PostMapping("/addReview")
    @ResponseBody
    public Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @DeleteMapping("/deleteReview/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

}
