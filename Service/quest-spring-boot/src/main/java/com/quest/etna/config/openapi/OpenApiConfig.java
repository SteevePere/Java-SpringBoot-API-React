package com.quest.etna.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig
{
 
    @Bean
    public OpenAPI customOpenAPI()
    {
    	final String securitySchemeName = "bearerAuth";
    	
        return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(
                new Components()
                    .addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .info(new Info()
        		.title("Issue Tracker API")
        		.description(
    				"This is a Spring Boot RESTful service using springdoc-openapi and OpenAPI 3."
				)
        		.version("V1")
    		);
    }
}