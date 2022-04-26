package com.example.rds.bot.repository;

import com.example.domain.type.BotFlowStatus;
import com.example.rds.bot.entity.BotSessionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BotSessionRepository extends CrudRepository<BotSessionEntity, Long> {

    Optional<BotSessionEntity> findByServiceIdAndCid(String serviceId, String cid);

    List<BotSessionEntity> findByServiceIdAndStatusAndCreatedLessThan(String serviceId, BotFlowStatus status, Date thresholdDate);
}
