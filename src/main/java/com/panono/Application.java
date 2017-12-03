package com.panono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Configuration
public class Application {

	private static final String PROJECT_VERSION = "1.0";
	private static final String PROJECT_TITLE = "Panono coding exercise";
	private static final String PROJECT_DESCRIPTION = "This is the Panono coding exercise";
	private static final String SWAGGER_GROUP_NAME = "panono-coding-exercise";
	private static final String SWAGGER_BASE_PACKAGE = "com.panono.controller";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName(SWAGGER_GROUP_NAME).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage(SWAGGER_BASE_PACKAGE)).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(PROJECT_TITLE).description(PROJECT_DESCRIPTION).version(PROJECT_VERSION)
				.build();
	}
}
