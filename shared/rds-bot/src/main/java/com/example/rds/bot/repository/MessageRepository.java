package com.example.rds.bot.repository;

import com.example.rds.bot.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Long> {

    List<MessageEntity> findByServiceIdAndCidAndMessageNoGreaterThanOrderByMessageNoDesc(String serviceId, String cid, Long latestMessageNo);

    List<MessageEntity> findByServiceIdAndCidAndSessionIdOrderByMessageNoDesc(String serviceId, String cid, String sessionId);
}
