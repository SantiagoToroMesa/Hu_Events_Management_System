package com.manage_system.Events.Config.Doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new io.swagger.v3.oas.models.info.Info()
                        .title("Events Management API")
                        .version("1.0")
                        .description("API documentation for the Events Management System")
                        .contact(new Contact()
                                .name("Santiago Toro")
                                .email("storom@gmail.com")
                                .url("localhost:8081"))

        );
    }
}
