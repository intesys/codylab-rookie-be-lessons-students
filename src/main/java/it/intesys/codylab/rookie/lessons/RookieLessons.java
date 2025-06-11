package it.intesys.codylab.rookie.lessons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RookieLessons {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RookieLessons.class);
        application.run(args);
    }
}
