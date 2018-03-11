package web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({InfrastructureMockConfig.class, ServiceMockConfig.class, SecurityMockConfig.class})
public class TestConfig {
}