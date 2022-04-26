package com.example.chatroom.app.chat;

import com.example.domain.rest.ApiResponse;
import com.example.domain.rest.bot.CurrentBotMessageListResponse;
import com.example.domain.rest.chatroom.ConnectIdByAuthTokenRequest;
import com.example.domain.rest.gateway.CurrentBotMessageListByConnectIdRequest;
import com.example.domain.rest.gateway.TextUtteranceByConnectIdRequest;
import com.example.domain.utils.ApiResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RequestMapping("/api/v1/chatroom")
@RestController
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @Operation(summary = "웹소켓 커넥션 ID 조회")
    @GetMapping("/connect-id")
    public ApiResponse<String> getConnectId(@ParameterObject @Valid ConnectIdByAuthTokenRequest request) {
        return ApiResponseHelper.ok(chatRoomService.getConnectIdByAuthToken(request));
    }

    @Operation(summary = "텍스트 발화")
    @PostMapping("/message/text")
    public ApiResponse<?> textUtterance(@Valid TextUtteranceByConnectIdRequest request) {
        chatRoomService.textUtterance(request);
        return ApiResponseHelper.ok();
    }

    @Operation(summary = "대화목록 조회")
    @GetMapping("/messages")
    public ApiResponse<CurrentBotMessageListResponse> getCurrentBotMessagesListByConnectId(@ParameterObject @Valid CurrentBotMessageListByConnectIdRequest request) {
        return ApiResponseHelper.ok(chatRoomService.getCurrentBotMessagesListByConnectId(request));
    }
}
