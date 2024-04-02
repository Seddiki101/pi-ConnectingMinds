package com.esprit.resourcesmanagement.servicesImpl;

import com.esprit.resourcesmanagement.daos.CategoryDao;
import com.esprit.resourcesmanagement.entities.Category;
import com.esprit.resourcesmanagement.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Implementation class of Category services.
 */

@Service
public class CategoryServiceImpl implements CategoryService {
    /**
     * Internal Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private static final String ERROR_NULL_ID = "Posted ID is NULL";
    private static final String ERROR_NON_PRESENT_ID = "Cannot find a categorie with id : %s";

    private static final String ERROR_UPDATE = "Error occurred while updating";

    @Resource
    private CategoryDao categoryDao;


    @Override
    public List<Category> getAllCategories() {
        return this.categoryDao.findAll();
    }

    @Override
    public Category findCategoryById(Long id) {
        Category category = null;
        if (id != null) {
            final Optional<Category> optionalCategory = this.categoryDao.findById(id);
            if (optionalCategory.isPresent()) {
                category = optionalCategory.get();
            } else {
                LOG.info(String.format(ERROR_NON_PRESENT_ID, id));
            }
        } else {
            LOG.error(ERROR_NULL_ID);
        }
        return category;
    }

    @Override
    public Category updateCategory(Category category) {
        Category updatedCategory = null;
        if (category != null) {
            updatedCategory = this.categoryDao.save(category);
        } else {
            LOG.error(ERROR_UPDATE);
        }
        return updatedCategory;
    }

    @Override
    public Category addCategory(Category category) {
        return this.categoryDao.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        this.categoryDao.deleteById(id);
    }

}
