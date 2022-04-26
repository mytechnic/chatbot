package com.example.bot.app;

import com.example.domain.rest.ApiResponse;
import com.example.domain.rest.bot.BotPrimaryKey;
import com.example.domain.rest.bot.BotUtteranceMessageRequest;
import com.example.domain.rest.bot.CurrentBotMessageListByCidRequest;
import com.example.domain.rest.bot.CurrentBotMessageListResponse;
import com.example.domain.utils.ApiResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RequestMapping("/bot/v1")
@RestController
@RequiredArgsConstructor
public class BotController {
    private final BotService botService;

    @Operation(summary = "챗봇 세션 ID 조회")
    @GetMapping("/session-id")
    public ApiResponse<String> getSessionId(@ParameterObject @Valid BotPrimaryKey request) {

        return ApiResponseHelper.ok(botService.getSessionId(request));
    }

    @Operation(summary = "발화")
    @PostMapping("/utterance")
    public ApiResponse<?> utterance(@RequestBody @Valid BotUtteranceMessageRequest request) {
        botService.utterance(request);
        return ApiResponseHelper.ok();
    }

    @Operation(summary = "대화목록 조회")
    @GetMapping("/messages")
    public ApiResponse<CurrentBotMessageListResponse> getCurrentBotMessagesListByCid(@ParameterObject @Valid CurrentBotMessageListByCidRequest request) {
        return ApiResponseHelper.ok(botService.getCurrentBotMessagesListByCid(request));
    }
}
