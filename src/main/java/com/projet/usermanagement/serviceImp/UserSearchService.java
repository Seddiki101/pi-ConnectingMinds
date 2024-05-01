package com.projet.usermanagement.serviceImp;

import com.projet.usermanagement.entity.User;
import com.projet.usermanagement.entity.UserRole;
import jakarta.persistence.EntityManager;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserSearchService {

    private static final Logger LOG = LoggerFactory.getLogger(UserSearchService.class);
    private static final String INDEXATION_ERROR = "Indexation process was interrupted";
    private static final String INDEXATION_OK = "Indexation completed successfully";

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void fullIndexation() {
        try {
            SearchSession searchSession = Search.session(entityManager);
            searchSession.massIndexer().startAndWait();
            LOG.info(INDEXATION_OK);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.error(INDEXATION_ERROR, e);
        }
    }

    @Transactional
    public List<User> findUsersByKeyword(String keyword) {
        SearchSession searchSession = Search.session(entityManager);

        List<User> users = searchSession.search(User.class)
                .where(f -> f.match()
                        .fields("firstName", "lastName", "email", "phone", "address")
                        .matching(keyword)
                        .fuzzy(2))  // Optional: apply fuzzy search to allow for slight misspellings
                .fetchHits(20);  // Fetch up to 20 hits

        return users;
    }



    @Transactional
    public List<User> findUsersByKeyword1(String keyword)  {
        SearchSession searchSession = Search.session(entityManager);

        List<User> users = searchSession.search(User.class)
                .where(f -> f.bool()
                        .must(f.match()
                                .fields("firstName", "lastName", "email", "phone", "address")
                                .matching(keyword)
                                .fuzzy(2)) // Optional: apply fuzzy search to allow for slight misspellings
                        .must(f.match()
                                .field("role")
                                .matching(UserRole.USER))) // Ensure only users with role ADMIN are fetched
                .fetchHits(20); // Fetch up to 20 hits

        return users;
    }




    @Transactional
    public List<User> findUsersByKeyword2(String keyword)  {
        SearchSession searchSession = Search.session(entityManager);

        List<User> users = searchSession.search(User.class)
                .where(f -> f.bool()
                        .must(f.match()
                                .fields("firstName", "lastName", "email", "phone", "address")
                                .matching(keyword)
                                .fuzzy(2)) // Optional: apply fuzzy search to allow for slight misspellings
                        .must(f.match()
                                .field("role")
                                .matching(UserRole.ADMIN))) // Ensure only users with role ADMIN are fetched
                .fetchHits(20); // Fetch up to 20 hits

        return users;
    }





}
