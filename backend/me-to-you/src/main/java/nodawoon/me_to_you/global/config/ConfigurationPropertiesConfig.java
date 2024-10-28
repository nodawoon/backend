package nodawoon.me_to_you.global.config;

import nodawoon.me_to_you.global.property.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({JwtProperties.class})
@Configuration
public class ConfigurationPropertiesConfig {
}
