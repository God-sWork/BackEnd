package gods.work.backend.config;

import gods.work.backend.constants.WebConstants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme auth = new SecurityScheme()
                .name(WebConstants.HEADER_AUTHORIZATION)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(WebConstants.HEADER_AUTHORIZATION, auth))
                .addSecurityItem(new SecurityRequirement().addList(WebConstants.HEADER_AUTHORIZATION));
    }
}
