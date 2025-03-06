package com.gutu.tasksmanager.service.impl;

import com.gutu.tasksmanager.entity.TaskEntity;
import com.gutu.tasksmanager.models.model.CreateUpdateTaskRequest;
import com.gutu.tasksmanager.models.model.TaskWithDetails;
import com.gutu.tasksmanager.repository.TaskRepository;
import com.gutu.tasksmanager.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TaskServiceImpl class is a service class that implements the task service.
 *
 * @author gutu.daniil
 * @version 1.0
 * @since 06.03.2025
 */
@Service
@Slf4j
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * The createTask method is used to create a new task.
     *
     * @param request the request object that contains the task details.
     * @return the response object that contains the created task details.
     */
    @Override
    @Transactional
    public TaskWithDetails createTask(CreateUpdateTaskRequest request) {
        TaskEntity task = new TaskEntity();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());

        return mapToTaskWithDetails(taskRepository.save(task));
    }

    /**
     * The getAllTasks method is used to get all tasks.
     *
     * @return the response object that contains the list of tasks.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TaskWithDetails> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::mapToTaskWithDetails)
                .collect(Collectors.toList());
    }

    /**
     * The getTaskById method is used to get a task by its id.
     *
     * @param id the task id.
     * @return the response object that contains the task details.
     */
    @Override
    @Transactional(readOnly = true)
    public TaskWithDetails getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(this::mapToTaskWithDetails)
                .orElse(null);
    }

    /**
     * The updateTask method is used to update a task.
     *
     * @param id the task id.
     * @param request the request object that contains the task details.
     * @return the response object that contains the updated task details.
     */
    @Override
    @Transactional
    public TaskWithDetails updateTask(Long id, CreateUpdateTaskRequest request) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(request.getTitle());
                    task.setDescription(request.getDescription());
                    task.setStatus(request.getStatus());
                    return mapToTaskWithDetails(taskRepository.save(task));
                })
                .orElse(null);
    }

    /**
     * The deleteTask method is used to delete a task.
     *
     * @param id the task id.
     * @return the response object that contains the status of the operation.
     */
    @Override
    @Transactional
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * The mapToTaskWithDetails method is used to map a task entity to a task with details object.
     *
     * @param task the task entity.
     * @return the task with details object.
     */
    private TaskWithDetails mapToTaskWithDetails(TaskEntity task) {
        var details = new TaskWithDetails();
        details.setId(task.getId());
        details.setTitle(task.getTitle());
        details.setDescription(task.getDescription());
        details.setStatus(task.getStatus());
        details.setCreatedAt(task.getCreatedAt());
        details.setUpdatedAt(task.getUpdatedAt());
        return details;
    }
}
