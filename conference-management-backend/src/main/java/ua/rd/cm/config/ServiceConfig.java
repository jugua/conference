package ua.rd.cm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yaroslav_Revin
 */

@Configuration
@ComponentScan(basePackages = "ua.rd.cm.service")
public class ServiceConfig {
}
