package br.com.ekan.avaliacao.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.StringJoiner;

@Configuration
public class OpenAPIConfig {
    private static final String SCHEME_NAME = "bearerAuth";

    private static final String SCHEME = "Bearer";

    @Bean
    GroupedOpenApi publicApi() {
       return GroupedOpenApi.builder()
               .group("public-apis")
               .pathsToMatch("/**")
               .build();
    }

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Avaliação - Desenvolvedor Backend Java")
                        .version("1.0.0")
                        .contact(new Contact()
                                .email("emersondiaspd@gmail.com")
                                .name("Emerson Dias de Oliveira"))
                        .license(new License()
                                    .name("Unlicense")
                                    .url("https://unlicense.org/")))
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                .components(
                        new Components()
                                .addSecuritySchemes(SCHEME_NAME, new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme(SCHEME)
                                        .description(createDescription())
                                        .bearerFormat("JWT")));
    }

    private String createDescription() {
        StringJoiner description = new StringJoiner("<br />");
        description.add("Cabeçalho de autorização JWT usando o esquema Bearer.");
        description.add("Informe seu token na entrada de texto abaixo.");
        return description.toString();
    }

}
