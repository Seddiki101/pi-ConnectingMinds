package com.tn.esprit.kanbanboard.service;

import com.tn.esprit.kanbanboard.entity.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<Group> findAll();
    Optional<Group> findById(Long ID);
    Group create(Long projectId,Group group);
    Group update(Group group);
    void delete(Group group);
}
