package com.gutu.tasksmanager.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApiConfig class is used to configure OpenAPI.
 *
 * @author gutu.daniil
 * @version 1.0
 * @since 06.03.2025
 */
@Configuration
public class OpenApiConfig {

    /**
     * The customOpenAPI method is used to create a custom OpenAPI object.
     *
     * @return the OpenAPI object.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPIV3Parser()
                .read("swagger/api_spec.yaml");
    }
}
