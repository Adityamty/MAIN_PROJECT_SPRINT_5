package com.nisum.cartAndCheckout.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI cartAndCheckoutServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cart and Checkout Service API")
                        .description("REST API for Cart and Checkout Management")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Nisum Development Team")
                                .email("support@nisum.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server().url("http://localhost:8085").description("Direct Service Access"),
                        new Server().url("http://localhost:8000").description("API Gateway Access")
                ));
    }
}
