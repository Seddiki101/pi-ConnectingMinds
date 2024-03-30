package com.tn.esprit.kanbanboard.dao;

import com.tn.esprit.kanbanboard.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDao extends JpaRepository<Task,Long> {
}
