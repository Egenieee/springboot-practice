package org.springboot.practice.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // JPA Audiging 활성화
public class JpaConfig {}

// WebMvcTest는 일반적인 @Configuration은 스캔하지 않는다.