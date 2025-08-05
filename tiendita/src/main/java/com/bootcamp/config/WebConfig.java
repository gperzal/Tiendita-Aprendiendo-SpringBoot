
package com.bootcamp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Versión moderna: usar PathPatternParser con trailing slash matching
        configurer.getPathMatchConfigurer().setMatchOptionalTrailingSeparator(true);
    }
}