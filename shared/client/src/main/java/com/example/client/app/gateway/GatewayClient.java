package com.example.client.app.gateway;

import com.example.domain.rest.ApiResponse;
import com.example.domain.rest.bot.CurrentBotMessageListResponse;
import com.example.domain.rest.gateway.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gateway", url = "http://localhost:8081/gateway/v1")
public interface GatewayClient {

    @GetMapping("/connect-user/connect-id")
    ApiResponse<String> getConnectIdByUid(@SpringQueryMap ConnectIdByUidRequest request);

    @PostMapping("/message/text")
    ApiResponse<?> textUtterance(@RequestBody TextUtteranceRequest request);

    @PostMapping("/message/text-by-connect-id")
    ApiResponse<?> textUtteranceByConnectId(@RequestBody TextUtteranceByConnectIdRequest request);

    @PostMapping("/message/bot-event")
    ApiResponse<?> botEvent(@RequestBody BotEventRequest request);

    @GetMapping("/messages")
    ApiResponse<CurrentBotMessageListResponse> getCurrentBotMessagesListByConnectId(@SpringQueryMap CurrentBotMessageListByConnectIdRequest request);
}
