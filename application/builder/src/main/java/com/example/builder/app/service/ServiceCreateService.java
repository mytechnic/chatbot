package com.example.builder.app.service;

import com.example.core.exception.BusinessException;
import com.example.domain.error.ServiceError;
import com.example.domain.rest.service.ServiceCreateRequest;
import com.example.rds.service.entity.ServiceManagerEntity;
import com.example.rds.service.repository.ServiceManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ServiceCreateService {
    private final ServiceManagerRepository serviceManagerRepository;

    public Integer createService(ServiceCreateRequest request) {

        if (serviceManagerRepository.existsByServiceId(request.getServiceId())) {
            throw new BusinessException(ServiceError.DUP_SERVICE_ID);
        }

        if (serviceManagerRepository.existsByName(request.getName())) {
            throw new BusinessException(ServiceError.DUP_SERVICE_NAME);
        }

        ServiceManagerEntity entity = new ServiceManagerEntity();
        entity.setServiceId(request.getServiceId());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setCreated(new Date());
        serviceManagerRepository.save(entity);

        return entity.getServiceNo();
    }
}
