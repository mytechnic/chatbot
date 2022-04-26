package com.example.rds.service.repository;

import com.example.rds.service.entity.ConnectGuestHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectGuestHistoryRepository extends CrudRepository<ConnectGuestHistoryEntity, Long> {

}
