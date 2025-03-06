package com.gutu.tasksmanager.controller;

import com.gutu.tasksmanager.models.model.CreateUpdateTaskRequest;
import com.gutu.tasksmanager.models.model.TaskWithDetails;
import com.gutu.tasksmanager.service.ITaskService;
import com.gutu.tasksmanager.utils.TaskRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TaskController class is a REST controller that handles HTTP requests related to tasks.
 *
 * @author gutu.daniil
 * @version 1.0
 * @since 06.03.2025
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    /**
     * The taskService field is used to interact with the task service.
     */
    private final ITaskService taskService;

    /**
     * The taskRequestValidator field is used to validate task requests.
     */
    private final TaskRequestValidator taskRequestValidator;

    public TaskController(ITaskService taskService, TaskRequestValidator taskRequestValidator) {
        this.taskService = taskService;
        this.taskRequestValidator = taskRequestValidator;
    }

    /**
     * The createTask method is used to create a new task.
     *
     * @param request the request object that contains the task details.
     * @return the response object that contains the created task details.
     */
    @PostMapping
    public ResponseEntity<TaskWithDetails> createTask(@RequestBody CreateUpdateTaskRequest request) {
        taskRequestValidator.validateCreateRequest(request);
        return ResponseEntity.status(201).body(taskService.createTask(request));
    }

    /**
     * The getAllTasks method is used to get all tasks.
     *
     * @return the response object that contains the list of tasks.
     */
    @GetMapping
    public ResponseEntity<List<TaskWithDetails>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    /**
     * The getTaskById method is used to get a task by its id.
     *
     * @param id the task id.
     * @return the response object that contains the task details.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskWithDetails> getTaskById(@PathVariable Long id) {
        TaskWithDetails task = taskService.getTaskById(id);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    /**
     * The updateTask method is used to update a task.
     *
     * @param id the task id.
     * @param request the request object that contains the task details.
     * @return the response object that contains the updated task details.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<TaskWithDetails> updateTask(@PathVariable Long id, @RequestBody CreateUpdateTaskRequest request) {
        taskRequestValidator.validateUpdateRequest(id, request);
        TaskWithDetails task = taskService.updateTask(id, request);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    /**
     * The deleteTask method is used to delete a task.
     *
     * @param id the task id.
     * @return the response object that contains the status of the operation.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
