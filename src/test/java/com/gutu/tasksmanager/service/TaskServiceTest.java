package com.gutu.tasksmanager.service;

import com.gutu.tasksmanager.entity.TaskEntity;
import com.gutu.tasksmanager.models.model.CreateUpdateTaskRequest;
import com.gutu.tasksmanager.models.model.SimpleTask;
import com.gutu.tasksmanager.models.model.TaskWithDetails;
import com.gutu.tasksmanager.repository.TaskRepository;
import com.gutu.tasksmanager.service.impl.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TaskServiceTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void testCreateTask() {
        CreateUpdateTaskRequest request = new CreateUpdateTaskRequest();
        request.setTitle("Test Task");
        request.setDescription("Test Description");
        request.setStatus(SimpleTask.StatusEnum.valueOf("TODO"));

        TaskWithDetails result = taskService.createTask(request);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    void testGetAllTasks() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle("Test Task");
        taskEntity.setDescription("Test Description");
        taskEntity.setStatus(SimpleTask.StatusEnum.valueOf("TODO"));
        taskRepository.save(taskEntity);

        List<TaskWithDetails> result = taskService.getAllTasks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
    }

    @Test
    void testGetAllTasks_Empty() {
        List<TaskWithDetails> result = taskService.getAllTasks();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTaskById() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle("Test Task");
        taskEntity.setDescription("Test Description");
        taskEntity.setStatus(SimpleTask.StatusEnum.valueOf("TODO"));
        taskEntity = taskRepository.save(taskEntity);

        TaskWithDetails result = taskService.getTaskById(taskEntity.getId());

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    void testGetTaskById_NotFound() {
        TaskWithDetails result = taskService.getTaskById(999L);

        assertNull(result);
    }

    @Test
    void testUpdateTask() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle("Test Task");
        taskEntity.setDescription("Test Description");
        taskEntity.setStatus(SimpleTask.StatusEnum.valueOf("TODO"));
        taskEntity = taskRepository.save(taskEntity);

        CreateUpdateTaskRequest request = new CreateUpdateTaskRequest();
        request.setTitle("Updated Task");
        request.setDescription("Updated Description");
        request.setStatus(SimpleTask.StatusEnum.valueOf("IN_PROGRESS"));

        TaskWithDetails result = taskService.updateTask(taskEntity.getId(), request);

        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
    }

    @Test
    void testUpdateTask_NotFound() {
        CreateUpdateTaskRequest request = new CreateUpdateTaskRequest();
        request.setTitle("Updated Task");
        request.setDescription("Updated Description");
        request.setStatus(SimpleTask.StatusEnum.valueOf("IN_PROGRESS"));

        TaskWithDetails result = taskService.updateTask(999L, request);

        assertNull(result);
    }

    @Test
    void testDeleteTask() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle("Test Task");
        taskEntity.setDescription("Test Description");
        taskEntity.setStatus(SimpleTask.StatusEnum.valueOf("TODO"));
        taskEntity = taskRepository.save(taskEntity);

        boolean result = taskService.deleteTask(taskEntity.getId());

        assertTrue(result);
        assertFalse(taskRepository.existsById(taskEntity.getId()));
    }

    @Test
    void testDeleteTask_NotFound() {
        boolean result = taskService.deleteTask(999L);

        assertFalse(result);
    }
}