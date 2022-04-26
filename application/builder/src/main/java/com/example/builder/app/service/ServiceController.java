package com.example.builder.app.service;

import com.example.domain.rest.ApiResponse;
import com.example.domain.rest.service.ServiceCreateRequest;
import com.example.domain.rest.service.ServiceDetailsResponse;
import com.example.domain.utils.ApiResponseHelper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RequestMapping("/management/v1/service")
@RestController
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceCreateService serviceCreateService;
    private final ServiceRetrieveService serviceRetrieveService;

    @Operation(summary = "서비스 생성")
    @PostMapping
    public ApiResponse<Integer> createService(@RequestBody @Valid ServiceCreateRequest request) {
        return ApiResponseHelper.ok(serviceCreateService.createService(request));
    }

    @Operation(summary = "서비스 조회")
    @GetMapping("/{serviceNo}")
    public ApiResponse<ServiceDetailsResponse> getServiceDetails(@PathVariable Integer serviceNo) {
        return ApiResponseHelper.ok(serviceRetrieveService.getServiceDetails(serviceNo));
    }

    @Operation(summary = "서비스 목록")
    @GetMapping("")
    public ApiResponse<List<ServiceDetailsResponse>> getServiceList() {
        return ApiResponseHelper.ok(serviceRetrieveService.getServiceList());
    }
}
