package com.example.demo01;

import com.example.demo01.core.EmailService.EmailSenderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
        info = @Info(
                title = "miniCrm",
                version = "1.0",
                description = "API documentation for mini crm"
        )
)
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class Demo01Application {

        public static void main(String[] args) {
            SpringApplication.run(Demo01Application.class, args);
        }

}
