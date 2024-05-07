package com.tn.esprit.kanbanboard.aspect;

import com.tn.esprit.kanbanboard.entity.*;
import com.tn.esprit.kanbanboard.enums.Status;
import com.tn.esprit.kanbanboard.kafkaProducer.NotificationProducer;
import com.tn.esprit.kanbanboard.service.ProjectService;
import com.tn.esprit.kanbanboard.service.TeamService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Aspect
@Component

public class ServiceExecutionAspect {
    private final NotificationProducer notificationProducer;
    private final ProjectService projectService;

    @Autowired
    public ServiceExecutionAspect(NotificationProducer notificationProducer,ProjectService projectService,TeamService teamService) {
        this.notificationProducer = notificationProducer;
        this.projectService = projectService;
    }
    @AfterReturning(pointcut = "execution(* com.tn.esprit.kanbanboard.service.TaskService.update(..)) && args(task)",
            returning = "result")
    public void afterTaskUpdate(Task task, Task result) {
        // Check if task status is updated to "Done"
        if(task!=null && result!=null) {
            Date previous= task.getDeadLine();
            Date next= result.getDeadLine();
            int comparison = previous.compareTo(next);

            if (task.getStatus() == Status.DONE) {
                if (result.getTeam() != null) {
                    Optional<Project> optionalProject = this.projectService.findByTeamId(result.getTeam().getTeamId());
                    if (optionalProject.isPresent()) {
                        Project project = optionalProject.get();
                        Notification notification = this.createNotificationTask(task, project, result.getTeam());
                        notification.setContent("• Task '" + task.getName() + "'-Status Updated to 'Done'"+ " in Project : "+project.getName()+" .");

                        notificationProducer.sendNotification(notification);
                    }
                }
            }else{
                if (result.getTeam() != null) {
                    Optional<Project> optionalProject = this.projectService.findByTeamId(result.getTeam().getTeamId());
                    if (optionalProject.isPresent()) {
                        Project project = optionalProject.get();
                        Notification notification = this.createNotificationTask(task, project, result.getTeam());
                        notification.setContent("• Task '" + task.getName() + "'-Dead Line Updated To " + formatDate(task.getDeadLine()) +" in Project : "+project.getName()+" .");

                        notificationProducer.sendNotification(notification);
                    }
                }
            }

        }
    }
    //create :
    @AfterReturning(pointcut = "execution(* com.tn.esprit.kanbanboard.service.TaskService.create(..)) && args(teamId,task)",
            returning = "result")
    public void afterTaskCreate(Long teamId,Task task, Task result) {

        // Send notification for task creation
        if (result != null && result.getTaskId() != null) {

            Optional<Project> optionalProject = this.projectService.findByTeamId(result.getTeam().getTeamId());
            if(optionalProject.isPresent()){

                Project project = optionalProject.get();
                Notification notification = this.createNotificationTask(task,project,result.getTeam());

                notification.setContent("• Task '" +task.getName() + "'-Has been added to '"+project.getName()+"' .");

                notificationProducer.sendNotification(notification);
            }
        }
    }
    //------Events :
    @AfterReturning(pointcut = "execution(* com.tn.esprit.kanbanboard.service.EventService.create(..)) && args(teamId,event)",
            returning = "result")
    public void afterEventCreate(Long teamId, Event event, Event result) {

        if (result != null && result.getEventId() != null) {

            Optional<Project> optionalProject = this.projectService.findByTeamId(teamId);
            if(optionalProject.isPresent()){

                Project project = optionalProject.get();
                Notification notification = this.createNotificationEvent(event,project,result.getTeam());

                notification.setContent("• Event '" +event.getName() + "'-Is set From '"+formatDateTime(event.getStartDate())+"' To '"+formatDateTime(event.getEndDate())+"'"+ " in Project : "+project.getName()+" .");

                notificationProducer.sendNotification(notification);
            }
        }
    }
    @AfterReturning(pointcut = "execution(* com.tn.esprit.kanbanboard.service.EventService.update(..)) && args(event)",
            returning = "result")
    public void afterEventUpdate(Event event, Event result) {

        if (result != null && result.getEventId() != null) {

            Optional<Project> optionalProject = this.projectService.findByTeamId(result.getTeamId());
            if(optionalProject.isPresent()){

                Project project = optionalProject.get();
                Notification notification = this.createNotificationEvent(event,project,result.getTeam());

                notification.setContent("• Updated Event '" +event.getName() + "'-Is now set From '"+formatDateTime(event.getStartDate())+"' To '"+formatDateTime(event.getEndDate())+"'"+ " in Project : "+project.getName()+" .");

                notificationProducer.sendNotification(notification);
            }
        }
    }
    private Notification createNotificationTask(Task task,Project project,Team team){
        Notification notification = new Notification();
        notification.setMemberId(task.getMemberId());
        notification.setProjectId(project.getProjectId());
        notification.setTeamId(team.getTeamId());
        notification.setIsOpened(false);
        notification.setCreatedAt(new Date());
        return notification;
    }
    private Notification createNotificationEvent(Event event,Project project,Team team){
        Notification notification = new Notification();
        notification.setProjectId(project.getProjectId());
        notification.setTeamId(team.getTeamId());
        notification.setIsOpened(false);
        notification.setCreatedAt(new Date());
        return notification;
    }
    public static String formatDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    // Method to format date without time
    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
