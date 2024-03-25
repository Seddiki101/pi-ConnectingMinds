package com.esprit.resourcesmanagement.servicesImpl;

import com.esprit.resourcesmanagement.daos.CategorieDao;
import com.esprit.resourcesmanagement.entities.Categorie;
import com.esprit.resourcesmanagement.services.CategorieService;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Implementation class of Categorie services.
 */

@Service
public class CategorieServiceImpl implements CategorieService {
    /**
     * Internal Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CategorieServiceImpl.class);
    private static final String ERROR_NULL_ID = "Posted ID is NULL";
    private static final String ERROR_NON_PRESENT_ID = "Cannot find a categorie with id : %s";

    private static final String ERROR_UPDATE = "Error occurred while updating";

    @Resource
    private CategorieDao categorieDao;


    @Override
    public List<Categorie> getAllCategories() {
        return this.categorieDao.findAll();
    }

    @Override
    public Categorie findCategorieById(Long id) {
        Categorie categorie = null;
        if (id != null) {
            final Optional<Categorie> optionalCategorie = this.categorieDao.findById(id);
            if (optionalCategorie.isPresent()) {
                categorie = optionalCategorie.get();
            } else {
                LOG.info(String.format(ERROR_NON_PRESENT_ID, id));
            }
        } else {
            LOG.error(ERROR_NULL_ID);
        }
        return categorie;
    }

    @Override
    public Categorie updateCategorie(Categorie categorie) {
        Categorie updatedCategorie = null;
        if (categorie != null) {
            updatedCategorie = this.categorieDao.save(categorie);
        } else {
            LOG.error(ERROR_UPDATE);
        }
        return updatedCategorie;
    }

    @Override
    public Categorie addCategorie(Categorie categorie) {
        return this.categorieDao.save(categorie);
    }

    @Override
    public void deleteCategorie(Long id) {
        this.categorieDao.deleteById(id);
    }

}
