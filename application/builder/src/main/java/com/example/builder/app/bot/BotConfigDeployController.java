package com.example.builder.app.bot;

import com.example.domain.rest.ApiResponse;
import com.example.domain.utils.ApiResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/management/v1/bot/config")
@RestController
@RequiredArgsConstructor
public class BotConfigDeployController {
    private final BotConfigDeployService botConfigDeployService;

    @Operation(summary = "봇 설정 배포")
    @PostMapping("/deploy")
    public ApiResponse<?> applyBotConfig() {
        botConfigDeployService.deploy();
        return ApiResponseHelper.ok();
    }
}
