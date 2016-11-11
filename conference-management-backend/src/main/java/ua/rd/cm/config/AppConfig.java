package ua.rd.cm.config;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import({RepositoryConfig.class, ServiceConfig.class, WebMvcConfig.class})
public class AppConfig {
}
