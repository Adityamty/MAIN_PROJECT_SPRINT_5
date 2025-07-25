package com.nisum.productmanagement.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsConfig {
    
    // CORS configuration disabled for individual services
    // API Gateway handles CORS for all services
    
    // @Bean
    // public CorsFilter corsFilter() {
    //     CorsConfiguration corsConfiguration = new CorsConfiguration();
    //     corsConfiguration.setAllowCredentials(true);
    //     corsConfiguration.addAllowedOriginPattern("*");
    //     corsConfiguration.addAllowedHeader("*");
    //     corsConfiguration.addAllowedMethod("*");
    //     
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", corsConfiguration);
    //     
    //     return new CorsFilter(source);
    // }
}
