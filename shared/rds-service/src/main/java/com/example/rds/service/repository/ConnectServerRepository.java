package com.example.rds.service.repository;

import com.example.domain.type.ConnectServerStatus;
import com.example.rds.service.entity.ConnectServerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConnectServerRepository extends CrudRepository<ConnectServerEntity, Long> {

    Optional<ConnectServerEntity> findByUserNo(Long userNo);

    Optional<ConnectServerEntity> findByUserNoAndStatus(Long userNo, ConnectServerStatus status);
}
