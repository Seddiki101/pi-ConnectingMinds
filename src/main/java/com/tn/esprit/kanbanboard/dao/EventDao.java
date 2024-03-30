package com.tn.esprit.kanbanboard.dao;

import com.tn.esprit.kanbanboard.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDao extends JpaRepository<Event,Long> {
}
