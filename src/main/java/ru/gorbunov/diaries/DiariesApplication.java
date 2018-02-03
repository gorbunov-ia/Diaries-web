package ru.gorbunov.diaries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application starter.
 */
@SpringBootApplication
@SuppressWarnings("checkstyle:hideutilityclassconstructor")
public class DiariesApplication {

    /**
     * Default java main method.
     *
     * @param args command line params
     */
    public static void main(String[] args) {
        SpringApplication.run(DiariesApplication.class, args);
    }
}
