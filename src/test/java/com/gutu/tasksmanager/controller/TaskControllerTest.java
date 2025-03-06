package com.gutu.tasksmanager.controller;

import com.gutu.tasksmanager.models.model.CreateUpdateTaskRequest;
import com.gutu.tasksmanager.models.model.TaskWithDetails;
import com.gutu.tasksmanager.service.ITaskService;
import com.gutu.tasksmanager.utils.TaskRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ITaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskRequestValidator taskRequestValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void testCreateTask() throws Exception {
        TaskWithDetails task = new TaskWithDetails();
        task.setId(1L);
        when(taskService.createTask(any(CreateUpdateTaskRequest.class))).thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Task\",\"description\":\"Test Description\",\"status\":\"NEW\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetAllTasks() throws Exception {
        TaskWithDetails task = new TaskWithDetails();
        task.setId(1L);
        List<TaskWithDetails> tasks = Collections.singletonList(task);
        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testGetTaskById() throws Exception {
        TaskWithDetails task = new TaskWithDetails();
        task.setId(1L);
        when(taskService.getTaskById(anyLong())).thenReturn(task);

        mockMvc.perform(get("/tasks/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateTask() throws Exception {
        TaskWithDetails task = new TaskWithDetails();
        task.setId(1L);
        when(taskService.updateTask(anyLong(), any(CreateUpdateTaskRequest.class))).thenReturn(task);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Task\",\"description\":\"Updated Description\",\"status\":\"IN_PROGRESS\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testDeleteTask() throws Exception {
        when(taskService.deleteTask(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetTaskById_NotFound() throws Exception {
        when(taskService.getTaskById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/tasks/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateTask_NotFound() throws Exception {
        when(taskService.updateTask(anyLong(), any(CreateUpdateTaskRequest.class))).thenReturn(null);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Task\",\"description\":\"Updated Description\",\"status\":\"IN_PROGRESS\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteTask_NotFound() throws Exception {
        when(taskService.deleteTask(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNotFound());
    }
}