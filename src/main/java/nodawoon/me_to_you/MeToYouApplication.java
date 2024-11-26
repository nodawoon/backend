package nodawoon.me_to_you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ConfigurationPropertiesScan
@EnableJpaAuditing
@SpringBootApplication
public class MeToYouApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeToYouApplication.class, args);
	}

}
