package nodawoon.me_to_you.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String jwtSchemeName = "jwtAuth";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .info(swaggerInfo())
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    private Info swaggerInfo() {
        License license = new License();
        license.setUrl("https://lab.ssafy.com/s11-final/S11P31B103");
        license.setName("너에게 난");

        return new Info()
                .version("v0.0.1")
                .title("\"너에게 난 API문서\"")
                .description("너에게 난의 API 문서 입니다.")
                .license(license);
    }

    private static List<String> getTags(HandlerMethod handlerMethod) {
        List<String> tags = new ArrayList<>();

        Tag[] methodTags = handlerMethod.getMethod().getAnnotationsByType(Tag.class);
        List<String> methodTagStrings =
                Arrays.stream(methodTags).map(Tag::name).collect(Collectors.toList());

        Tag[] classTags = handlerMethod.getClass().getAnnotationsByType(Tag.class);
        List<String> classTagStrings =
                Arrays.stream(classTags).map(Tag::name).collect(Collectors.toList());
        tags.addAll(methodTagStrings);
        tags.addAll(classTagStrings);
        return tags;
    }
}