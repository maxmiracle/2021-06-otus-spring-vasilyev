package org.maxvas.exercise7;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class Exercise7Application {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(Exercise7Application.class, args);

    }

}
