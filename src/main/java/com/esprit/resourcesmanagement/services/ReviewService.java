package com.esprit.resourcesmanagement.services;

import com.esprit.resourcesmanagement.entities.Review;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Review Service Manipulations.
 */
public interface ReviewService {
    /**
     * Fetch the List of reviews.
     *
     * @return list of Review.
     */
    List<Review> getAllReviews();

    /**
     * Fetch Review by a given id.
     *
     * @param id
     * @return Review
     */
    Review findReviewById(Long id);

    /**
     * Updates a given review's fields.
     *
     * @param review
     * @return updated review object.
     */
    Review updateReview(Review review);

    Review addReview(@RequestBody Review review);

    void deleteReview(Long id);
}
