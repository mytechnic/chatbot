package com.example.rds.service.repository;

import com.example.rds.service.entity.ServiceManagerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceManagerRepository extends CrudRepository<ServiceManagerEntity, Integer> {

    boolean existsByServiceId(String serviceId);

    boolean existsByName(String name);

    Optional<ServiceManagerEntity> findByServiceId(String serviceId);
}
