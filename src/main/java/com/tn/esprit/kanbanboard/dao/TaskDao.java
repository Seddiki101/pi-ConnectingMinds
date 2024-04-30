package com.tn.esprit.kanbanboard.dao;

import com.tn.esprit.kanbanboard.entity.Project;
import com.tn.esprit.kanbanboard.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<Task,Long> {
    @Query("SELECT t FROM Task t JOIN t.team team JOIN team.project project WHERE project = :project AND t.deadLine > :currentDate ORDER BY t.deadLine ASC")
    List<Task> retrieveUpcomingTasks(@Param("project") Project project, @Param("currentDate") Date currentDate);
}

