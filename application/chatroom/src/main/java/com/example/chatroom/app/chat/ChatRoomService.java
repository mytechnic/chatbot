package com.example.chatroom.app.chat;

import com.example.client.app.gateway.GatewayClient;
import com.example.core.exception.BusinessException;
import com.example.domain.error.CommonError;
import com.example.domain.rest.ApiResponse;
import com.example.domain.rest.bot.CurrentBotMessageListResponse;
import com.example.domain.rest.chatroom.ConnectIdByAuthTokenRequest;
import com.example.domain.rest.gateway.ConnectIdByUidRequest;
import com.example.domain.rest.gateway.CurrentBotMessageListByConnectIdRequest;
import com.example.domain.rest.gateway.TextUtteranceByConnectIdRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final GatewayClient gatewayClient;

    public String getConnectIdByAuthToken(ConnectIdByAuthTokenRequest request) {

        String uid;
        // TODO: 고객사 서버를 통해 authToken을 uid를 조회
        if ("star-chatbot".equals(request.getServiceId())) {
            uid = getUidBySampleChatBot(request.getAuthToken());
        } else {
            throw new BusinessException(CommonError.BAD_REQUEST);
        }

        ConnectIdByUidRequest connectIdByUidRequest = new ConnectIdByUidRequest();
        connectIdByUidRequest.setServiceId(request.getServiceId());
        connectIdByUidRequest.setChannel(request.getChannel());
        connectIdByUidRequest.setChannelId(request.getChannelId());
        connectIdByUidRequest.setUid(uid);
        ApiResponse<String> res = gatewayClient.getConnectIdByUid(connectIdByUidRequest);
        if (res.isFail()) {
            throw new BusinessException(res.getCode(), res.getMessage());
        }

        return res.getResult();
    }

    private String getUidBySampleChatBot(String authToken) {
        return "lovesbw";
    }

    public void textUtterance(TextUtteranceByConnectIdRequest request) {
        ApiResponse<?> res = gatewayClient.textUtteranceByConnectId(request);
        if (res.isFail()) {
            throw new BusinessException(res.getCode(), res.getMessage());
        }
    }

    public CurrentBotMessageListResponse getCurrentBotMessagesListByConnectId(CurrentBotMessageListByConnectIdRequest request) {
        ApiResponse<CurrentBotMessageListResponse> res = gatewayClient.getCurrentBotMessagesListByConnectId(request);
        if (res.isFail()) {
            throw new BusinessException(res.getCode(), res.getMessage());
        }
        return res.getResult();
    }
}
