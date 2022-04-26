package com.example.bot.scheduler;

import com.example.CoreApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackageClasses = CoreApplication.class)
public class ChatBotSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatBotSchedulerApplication.class, args);
    }

}
