package org.maxvas.exercise;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(MainApplication.class, args);

    }

}
