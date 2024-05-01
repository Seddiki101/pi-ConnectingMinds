package com.esprit.resourcesmanagement.servicesImpl;

import com.esprit.resourcesmanagement.daos.ResourceDao;
import com.esprit.resourcesmanagement.entities.Resource;
import com.esprit.resourcesmanagement.services.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



/**
 * Implementation class of Resource services.
 */

@Service
public class ResourceServiceImpl implements ResourceService {
    /**
     * Internal Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ResourceServiceImpl.class);
    private static final String ERROR_NULL_ID = "Posted ID is NULL";
    private static final String ERROR_NON_PRESENT_ID = "Cannot find a resource with id : %s";

    private static final String ERROR_UPDATE = "Error occurred while updating";

    @jakarta.annotation.Resource
    private ResourceDao resourceDao;


    @Override
    public List<Resource> getAllResources() {
        return this.resourceDao.findAll();
    }

    @Override
    public Resource findResourceById(Long id) {
        Resource resource = null;
        if (id != null) {
            final Optional<Resource> optionalResource = this.resourceDao.findById(id);
            if (optionalResource.isPresent()) {
                resource = optionalResource.get();
            } else {
                LOG.info(String.format(ERROR_NON_PRESENT_ID, id));
            }
        } else {
            LOG.error(ERROR_NULL_ID);
        }
        return resource;
    }

    @Override
    public Resource updateResource(Resource resource) {
        Resource updatedResource = null;
        if (resource != null) {
            updatedResource = this.resourceDao.save(resource);
        } else {
            LOG.error(ERROR_UPDATE);
        }
        return updatedResource;
    }

    @Override
    public Resource addResource(Resource resource) {
        return this.resourceDao.save(resource);
    }

    @Override
    public void deleteResource(Long id) {
        this.resourceDao.deleteById(id);
    }

    @Override
    public Resource getLastAddedResource() {
        Optional<Resource> optionalResource = resourceDao.findLastAddedResource();
        return optionalResource.orElse(null);
    }

    @Override
    public List<Resource> findTop4ResourcesByLikes() {
        List<Resource> topResources = new ArrayList<>();

        // Récupérer les ressources triées par le nombre de likes de manière décroissante
        List<Resource> resources = resourceDao.findAll(Sort.by(Sort.Direction.DESC, "likes"));

        // Sélectionner les 4 premières ressources ou toutes les ressources si elles sont moins de 4
        int count = Math.min(resources.size(), 4);
        for (int i = 0; i < count; i++) {
            topResources.add(resources.get(i));
        }

        return topResources;
    }


}
