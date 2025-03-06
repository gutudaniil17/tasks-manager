package com.gutu.tasksmanager.utils;

import com.gutu.tasksmanager.exception.ValidationException;
import com.gutu.tasksmanager.models.model.CreateUpdateTaskRequest;
import org.springframework.stereotype.Component;
import org.apache.commons.text.StringEscapeUtils;
import java.util.regex.Pattern;

/**
 * TaskRequestValidator class is used to validate task requests.
 *
 * @author gutu.daniil
 * @version 1.0
 * @since 06.03.2025
 */
@Component
public class TaskRequestValidator {

    // Pattern for basic input validation (allows letters, numbers, spaces, and basic punctuation)
    private static final Pattern SAFE_INPUT_PATTERN = Pattern.compile("^[\\p{L}\\p{N}\\s.,!?-]{1,}$");

    // Pattern for detecting common SQL injection attempts
    private static final Pattern SQL_INJECTION_PATTERN =
            Pattern.compile("(?i).*(\\b(select|insert|update|delete|drop|union|exec|declare)\\b).*");

    // Pattern for detecting XSS attempts
    private static final Pattern XSS_PATTERN =
            Pattern.compile("(?i).*(<script|javascript:|onload=|onerror=|<img|<iframe).*");

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
        if (id <= 0) {
            throw new ValidationException("Invalid task ID");
        }
        if (request == null) {
            throw new ValidationException("Request body cannot be null");
        }
        validateCommonFields(request);
    }

    private void validateCommonFields(CreateUpdateTaskRequest request) {
        // Title validation
        String title = request.getTitle();
        if (isBlank(title)) {
            throw new ValidationException("Title is required");
        }
        validateInput("title", title, 100);

        // Description validation
        String description = request.getDescription();
        if (isBlank(description)) {
            throw new ValidationException("Description is required");
        }
        validateInput("description", description, 500);

        // Status validation
        if (request.getStatus() == null) {
            throw new ValidationException("Status is required");
        }
        validateStatus(request.getStatus().getValue());
    }

    private void validateInput(String fieldName, String input, int maxLength) {
        // Length check
        if (input.length() > maxLength) {
            throw new ValidationException(fieldName + " must not exceed " + maxLength + " characters");
        }

        // SQL Injection check
        if (SQL_INJECTION_PATTERN.matcher(input).matches()) {
            throw new ValidationException("Invalid " + fieldName + ": potential SQL injection detected");
        }

        // XSS check
        if (XSS_PATTERN.matcher(input).matches()) {
            throw new ValidationException("Invalid " + fieldName + ": potential XSS attack detected");
        }

        // Safe input pattern check
        if (!SAFE_INPUT_PATTERN.matcher(input).matches()) {
            throw new ValidationException("Invalid " + fieldName + ": contains invalid characters");
        }

        // Sanitize the input
        String sanitized = sanitizeInput(input);
        if (!input.equals(sanitized)) {
            throw new ValidationException("Invalid " + fieldName + ": contains potentially dangerous content");
        }
    }

    private void validateStatus(String status) {
        try {
            if (!status.matches("^(TODO|IN_PROGRESS|DONE)$")) {
                throw new ValidationException("Invalid status value. Must be TODO, IN_PROGRESS, or DONE");
            }
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid status value");
        }
    }

    private String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        // HTML encode special characters
        String sanitized = StringEscapeUtils.escapeHtml4(input);
        // Remove any remaining potentially dangerous characters
        sanitized = sanitized.replaceAll("[<>\"'%;()&+]", "");
        return sanitized.trim();
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}