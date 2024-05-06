package com.esprit.resourcesmanagement.servicesImpl;

import com.esprit.resourcesmanagement.daos.ReviewDao;
import com.esprit.resourcesmanagement.entities.Review;
import com.esprit.resourcesmanagement.services.ResourceService;
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

    @Resource
    private ResourceService resourceService ;


    @Override
    public List<Review> getAllReviews() {
        return this.reviewDao.findAll();
    }

    @Override
    public List<Review> getAllReviewsByResource(Long resourceId) {
        return this.reviewDao.findByResource_ResourceId(resourceId);
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
    public Review updateReview(Review review,Long id) {
        Review ancienneReview =this.findReviewById(id);
        if (review != null) {

            ancienneReview.setContent(review.getContent());
            this.reviewDao.save(ancienneReview);
        } else {
            LOG.error(ERROR_UPDATE);
        }
        return ancienneReview;
    }

    @Override
    public Review addReview(Review review ) {
       // Resource resource = (Resource) this.resourceService.findResourceById(resourceId);
       // review.setResource((com.esprit.resourcesmanagement.entities.Resource) resource);
        return this.reviewDao.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        this.reviewDao.deleteById(id);
    }

}
