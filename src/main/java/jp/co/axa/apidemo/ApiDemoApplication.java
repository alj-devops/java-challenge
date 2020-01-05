package jp.co.axa.apidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Start class for the spring boot.
 * 
 * @author AXA
 *
 */
@EnableSwagger2
@SpringBootApplication
public class ApiDemoApplication {

    /**
     * main Function for spring boot.
     * 
     * @param args start parameter
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiDemoApplication.class, args);
    }

}
