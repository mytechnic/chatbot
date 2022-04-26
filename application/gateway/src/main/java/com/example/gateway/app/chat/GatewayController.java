package com.example.gateway.app.chat;

import com.example.domain.rest.ApiResponse;
import com.example.domain.rest.bot.CurrentBotMessageListResponse;
import com.example.domain.rest.gateway.*;
import com.example.domain.utils.ApiResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RequestMapping("/gateway/v1")
@RestController
@RequiredArgsConstructor
public class GatewayController {
    private final GatewayService gatewayService;

    @Operation(summary = "클라이언트용 고객 식별 ID 조회")
    @GetMapping("/connect-user/connect-id")
    public ApiResponse<String> getConnectIdByUid(@ParameterObject @Valid ConnectIdByUidRequest request) {
        return ApiResponseHelper.ok(gatewayService.getConnectIdByUid(request));
    }

    @Operation(summary = "텍스트 발화")
    @PostMapping("/message/text")
    public ApiResponse<?> textUtterance(@RequestBody @Valid TextUtteranceRequest request) {
        gatewayService.textUtterance(request);
        return ApiResponseHelper.ok();
    }

    @Operation(summary = "텍스트 발화")
    @PostMapping("/message/text-by-connect-id")
    public ApiResponse<?> textUtteranceByConnectId(@RequestBody @Valid TextUtteranceByConnectIdRequest request) {
        gatewayService.textUtterance(request);
        return ApiResponseHelper.ok();
    }

    @Operation(summary = "수신 이벤트")
    @PostMapping("/message/bot-event")
    public ApiResponse<?> botEvent(@RequestBody @Valid BotEventRequest request) {
        gatewayService.botEvent(request);
        return ApiResponseHelper.ok();
    }

    @Operation(summary = "대화목록 조회")
    @GetMapping("/messages")
    public ApiResponse<CurrentBotMessageListResponse> getCurrentBotMessagesListByConnectId(@ParameterObject @Valid CurrentBotMessageListByConnectIdRequest request) {
        return ApiResponseHelper.ok(gatewayService.getCurrentBotMessagesListByConnectId(request));
    }
}
