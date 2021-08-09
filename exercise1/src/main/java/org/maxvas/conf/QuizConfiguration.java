package org.maxvas.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.maxvas"})
@PropertySource("classpath:application.properties")
public class QuizConfiguration {

}
