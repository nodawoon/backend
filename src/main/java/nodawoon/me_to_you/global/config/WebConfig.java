package nodawoon.me_to_you.global.config;

import lombok.RequiredArgsConstructor;
import nodawoon.me_to_you.domain.oauth.presentation.OauthServerTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://k11b103.p.ssafy.io/", "http://localhost:3000")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PATCH.name()
                )
                .allowCredentials(true)
                .exposedHeaders("*");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new OauthServerTypeConverter());
    }
}
