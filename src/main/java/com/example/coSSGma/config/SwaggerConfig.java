package com.example.coSSGma.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server")
                ));
    }

    private Info apiInfo() {
        return new Info()
                .title("Sspoid Swagger");
    }
}
