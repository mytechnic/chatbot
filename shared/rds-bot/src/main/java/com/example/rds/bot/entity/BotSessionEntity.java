package com.example.rds.bot.entity;

import com.example.domain.type.BotFlowStatus;
import com.example.domain.type.Channel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("session")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BotSessionEntity {

    @Id
    private Long sessionNo;
    private String serviceId;
    private String cid;
    private String sessionId;
    private Channel channel;
    private String channelId;
    private BotFlowStatus status;
    private String context;
    private Date messageCreated;
    private Date messageUpdated;
    private Date updated;
    private Date created;
}