package com.example.rds.service.repository;

import com.example.rds.service.entity.ConnectHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectHistoryRepository extends CrudRepository<ConnectHistoryEntity, Long> {

}
