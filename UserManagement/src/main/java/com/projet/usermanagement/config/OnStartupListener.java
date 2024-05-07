package com.projet.usermanagement.config;


import com.projet.usermanagement.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OnStartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserSearchService searchService;
    @Override
    @Async
    public void onApplicationEvent(ContextRefreshedEvent event) {
            searchService.fullIndexation();

            Thread.currentThread().interrupt(); // Properly handle thread interruption

    }
}
