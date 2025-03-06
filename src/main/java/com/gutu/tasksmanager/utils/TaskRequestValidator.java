package com.gutu.tasksmanager.utils;

import com.gutu.tasksmanager.exception.ValidationException;
import com.gutu.tasksmanager.models.model.CreateUpdateTaskRequest;
import org.springframework.stereotype.Component;

@Component
public class TaskRequestValidator {

    public void validateCreateRequest(CreateUpdateTaskRequest request) {
        if (request == null) {
            throw new ValidationException("Request body cannot be null");
        }
        validateCommonFields(request);
    }

    public void validateUpdateRequest(Long id, CreateUpdateTaskRequest request) {
        if (id == null) {
            throw new ValidationException("Task ID cannot be null");
        }
        if (request == null) {
            throw new ValidationException("Request body cannot be null");
        }
        validateCommonFields(request);
    }

    private void validateCommonFields(CreateUpdateTaskRequest request) {
        if (isBlank(request.getTitle())) {
            throw new ValidationException("Title is required");
        }
        if (request.getTitle().length() > 100) {
            throw new ValidationException("Title must not exceed 100 characters");
        }
        if (isBlank(request.getDescription())) {
            throw new ValidationException("Description is required");
        }
        if (request.getDescription().length() > 500) {
            throw new ValidationException("Description must not exceed 500 characters");
        }
        if (request.getStatus() == null) {
            throw new ValidationException("Status is required");
        }
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}