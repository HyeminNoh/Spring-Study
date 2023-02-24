package com.example.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @Component가 붙은걸 다 자동등록해줌
@ComponentScan (
        basePackages = "com.example.core",
        // @Configuration 내부에 @Component 선언되어 있어서 자동 주입 제외를 위해 설정 (configuration 예제들이 많아서 테스트 파일들을 위해..)
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
}
