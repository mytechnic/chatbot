package com.example.rds.service.repository;

import com.example.rds.service.entity.ConnectUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConnectUserRepository extends CrudRepository<ConnectUserEntity, Long> {

    Optional<ConnectUserEntity> findByServiceNoAndUid(Integer serviceNo, String uid);

    Optional<ConnectUserEntity> findByServiceNoAndConnectId(Integer serviceNo, String connectId);

    Optional<ConnectUserEntity> findByServiceNoAndCid(Integer serviceNo, String cid);
}
