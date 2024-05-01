package com.esprit.resourcesmanagement.servicesImpl;

import com.esprit.resourcesmanagement.daos.ReviewDao;
import com.esprit.resourcesmanagement.entities.Review;
import com.esprit.resourcesmanagement.services.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Implementation class of Review services.
 */

@Service
public class ReviewServiceImpl implements ReviewService {
    /**
     * Internal Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private static final String ERROR_NULL_ID = "Posted ID is NULL";
    private static final String ERROR_NON_PRESENT_ID = "Cannot find a review with id : %s";

    private static final String ERROR_UPDATE = "Error occurred while updating";

    @Resource
    private ReviewDao reviewDao;


    @Override
    public List<Review> getAllReviews() {
        return this.reviewDao.findAll();
    }

    @Override
    public Review findReviewById(Long id) {
        Review review = null;
        if (id != null) {
            final Optional<Review> optionalReview = this.reviewDao.findById(id);
            if (optionalReview.isPresent()) {
                review = optionalReview.get();
            } else {
                LOG.info(String.format(ERROR_NON_PRESENT_ID, id));
            }
        } else {
            LOG.error(ERROR_NULL_ID);
        }
        return review;
    }

    @Override
    public Review updateReview(Review review) {
        Review updatedReview = null;
        if (review != null) {
            updatedReview = this.reviewDao.save(review);
        } else {
            LOG.error(ERROR_UPDATE);
        }
        return updatedReview;
    }

    @Override
    public Review addReview(Review review) {
        return this.reviewDao.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        this.reviewDao.deleteById(id);
    }

}
