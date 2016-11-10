package ua.rd.cm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yaroslav_Revin
 */

@Configuration
@ComponentScan(basePackages = {"ua.rd.cm.domain", "ua.rd.cm.repository"})
public class RepositoryConfig {
}
