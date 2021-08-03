package lt.addendum.job.interview.task.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String BASIC_AUTH = "basicAuth";
    private static final String BASIC_PACKAGE = "lt.addendum.job.interview.task";

    @Value("${api.auth.activate.quality}")
    private String path;

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage(BASIC_PACKAGE)).build();
    }

    @Bean
    public Docket basicAuthApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("For basic authorization")
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASIC_PACKAGE))
                .paths(PathSelectors.ant(path))
                .build()
                .apiInfo(apiInfo())
                .securityContexts(Collections.singletonList(basicAuthSecurityContext()))
                .securitySchemes(Collections.singletonList(basicAuthScheme()));
    }

    private SecurityContext basicAuthSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(basicAuthReference()))
                .forPaths(PathSelectors.ant(path))
                .build();
    }

    private SecurityScheme basicAuthScheme() {
        return new BasicAuth(BASIC_AUTH);
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference(BASIC_AUTH, new AuthorizationScope[0]);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Job interview")
                .description("Authentication")
                .version("v1")
                .build();
    }
}

