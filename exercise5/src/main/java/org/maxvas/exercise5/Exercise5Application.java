package org.maxvas.exercise5;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Exercise5Application {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(Exercise5Application.class, args);

        Console.main(args);
    }

}
