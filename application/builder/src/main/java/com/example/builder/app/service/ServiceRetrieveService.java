package com.example.builder.app.service;

import com.example.core.exception.BusinessException;
import com.example.domain.error.ServiceError;
import com.example.domain.rest.service.ServiceDetailsResponse;
import com.example.rds.service.entity.ServiceManagerEntity;
import com.example.rds.service.repository.ServiceManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ServiceRetrieveService {
    private final ServiceManagerRepository serviceManagerRepository;

    public List<ServiceDetailsResponse> getServiceList() {
        return StreamSupport.stream(serviceManagerRepository.findAll().spliterator(), false)
                .map(this::getServiceDetailsResponse)
                .collect(Collectors.toList());
    }

    public ServiceDetailsResponse getServiceDetails(Integer serviceNo) {
        ServiceManagerEntity entity = serviceManagerRepository.findById(serviceNo)
                .orElseThrow(() -> new BusinessException(ServiceError.NOT_FOUND));

        return getServiceDetailsResponse(entity);
    }

    private ServiceDetailsResponse getServiceDetailsResponse(ServiceManagerEntity entity) {
        ServiceDetailsResponse response = new ServiceDetailsResponse();
        response.setServiceId(entity.getServiceId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setUpdated(entity.getUpdated());
        response.setCreated(entity.getCreated());
        return response;
    }
}
