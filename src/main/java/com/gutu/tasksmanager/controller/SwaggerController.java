package com.gutu.tasksmanager.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * SwaggerController class is used to get the API documentation.
 *
 * @author gutu.daniil
 * @version 1.0
 * @since 06.03.2025
 */
@RestController
public class SwaggerController {

    @GetMapping(value = "/api-docs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getApiDocs() throws IOException {
        Resource resource = new ClassPathResource("swagger/api_spec.yaml");
        byte[] bytes = resource.getInputStream().readAllBytes();
        return ResponseEntity.ok(new String(bytes, StandardCharsets.UTF_8));
    }
}