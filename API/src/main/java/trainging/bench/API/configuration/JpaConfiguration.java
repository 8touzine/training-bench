package trainging.bench.API.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "trainging.bench.API.repository")
public class JpaConfiguration {
}