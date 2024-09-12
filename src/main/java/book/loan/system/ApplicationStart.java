package book.loan.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class ApplicationStart extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}
