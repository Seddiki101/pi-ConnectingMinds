package com.esprit.resourcesmanagement.services;

import com.esprit.resourcesmanagement.entities.Resource;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Resource Service Manipulations.
 */
public interface ResourceService {
    /**
     * Fetch the List of resources.
     *
     * @return list of Resource.
     */
    List<Resource> getAllResources();
    List<Resource> getAllResourcesByUserId(Long id);

    /**
     * Fetch Resource by a given id.
     *
     * @param id
     * @return Resource
     */
    Resource findResourceById(Long id);

    /**
     * Updates a given resource's fields.
     *
     * @param resource
     * @return updated resource object.
     */
    Resource updateResource(Resource resource,Long id);

    Resource addResource(@RequestBody Resource resource);

    void deleteResource(Long id);
    Resource getLastAddedResource();
    List<Resource> findTop4ResourcesByLikes();

    void views (Long id);
    void ReviewsUp (Long id);
    void ReviewsDown (Long id);
    void like (Long id);




}
