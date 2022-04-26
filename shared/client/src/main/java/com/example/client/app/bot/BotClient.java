package com.example.client.app.bot;

import com.example.domain.rest.ApiResponse;
import com.example.domain.rest.bot.BotPrimaryKey;
import com.example.domain.rest.bot.BotUtteranceMessageRequest;
import com.example.domain.rest.bot.CurrentBotMessageListByCidRequest;
import com.example.domain.rest.bot.CurrentBotMessageListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bot", url = "http://localhost:8083/bot/v1")
public interface BotClient {

    @GetMapping("/session-id")
    ApiResponse<String> getSessionId(@SpringQueryMap BotPrimaryKey request);

    @PostMapping("/utterance")
    ApiResponse<?> utterance(@RequestBody BotUtteranceMessageRequest request);

    @GetMapping("/messages")
    ApiResponse<CurrentBotMessageListResponse> getCurrentBotMessagesListByCid(@SpringQueryMap CurrentBotMessageListByCidRequest request);
}
