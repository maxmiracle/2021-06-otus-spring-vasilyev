package org.maxvas.exercise6;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Exercise6Application {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(Exercise6Application.class, args);

        Console.main(args);
    }

}
