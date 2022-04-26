package com.example.bot.scheduler.task;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BotScheduler {
    private final BotService botService;

    @Scheduled(fixedRateString = "5000", initialDelayString = "10000")
    public void run() {
        botService.expireMessageSession();
    }
}
