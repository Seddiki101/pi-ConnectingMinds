//package com.esprit.resourcesmanagement.servicesImpl;
//
//import com.esprit.resourcesmanagement.daos.SubscribeDao;
//import com.esprit.resourcesmanagement.entities.Subscribe;
//import com.esprit.resourcesmanagement.services.SubscribeService;
//import jakarta.annotation.Resource;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class SubscribeServiceImpl implements SubscribeService {
//
//    @Resource
//    private SubscribeDao subscribeDao ;
//    @Override
//    public List<Subscribe> findByResource(Long resourceId) {
//        return this.subscribeDao.findByResource_ResourceId(resourceId);
//    }
//}
