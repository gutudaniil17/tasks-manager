package com.gutu.tasksmanager.service;

import com.gutu.tasksmanager.models.model.CreateUpdateTaskRequest;
import com.gutu.tasksmanager.models.model.TaskWithDetails;

import java.util.List;

/**
 * ITaskService interface is a service interface that provides methods to interact with tasks.
 *
 * @author gutu.daniil
 * @version 1.0
 * @since 06.03.2025
 */
public interface ITaskService {
    /**
     * The createTask method is used to create a new task.
     *
     * @param request the request object that contains the task details.
     * @return the response object that contains the created task details.
     */
    TaskWithDetails createTask(CreateUpdateTaskRequest request);

    /**
     * The getAllTasks method is used to get all tasks.
     *
     * @return the response object that contains the list of tasks.
     */
    List<TaskWithDetails> getAllTasks();

    /**
     * The getTaskById method is used to get a task by its id.
     *
     * @param id the task id.
     * @return the response object that contains the task details.
     */
    TaskWithDetails getTaskById(Long id);

    /**
     * The updateTask method is used to update a task.
     *
     * @param id the task id.
     * @param request the request object that contains the task details.
     * @return the response object that contains the updated task details.
     */
    TaskWithDetails updateTask(Long id, CreateUpdateTaskRequest request);

    /**
     * The deleteTask method is used to delete a task.
     *
     * @param id the task id.
     * @return a boolean value indicating the success of the operation.
     */
    boolean deleteTask(Long id);
}
