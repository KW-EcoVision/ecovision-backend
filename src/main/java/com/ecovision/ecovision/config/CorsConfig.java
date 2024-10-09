package com.ecovision.ecovision.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 모든 도메인 허용
        corsConfiguration.addAllowedOriginPattern("*");

        // 모든 HTTP 메서드 허용
        corsConfiguration.addAllowedMethod("*");

        // 모든 헤더 허용
        corsConfiguration.addAllowedHeader("*");

        // CORS요청에 대한 응답에서 쿠키를 허용
        corsConfiguration.setAllowCredentials(true);

        // 모든 헤더 노출 허용
        corsConfiguration.addExposedHeader("*");

        // 모든 엔드포인트에 CORS설정 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}