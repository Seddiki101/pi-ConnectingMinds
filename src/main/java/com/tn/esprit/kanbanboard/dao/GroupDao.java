package com.tn.esprit.kanbanboard.dao;

import com.tn.esprit.kanbanboard.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupDao extends JpaRepository<Group,Long> {
}
