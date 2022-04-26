package com.example.rds.bot.entity;

import com.example.domain.type.BlockType;
import com.example.domain.type.Channel;
import com.example.domain.type.MessageWriteType;
import com.example.domain.type.ScenarioType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("message")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MessageEntity {

    @Id
    private Long messageNo;
    private String serviceId;
    private String cid;
    private String sessionId;
    private ScenarioType scenarioType;
    private String scenario;
    private BlockType blockType;
    private String block;
    private Channel channel;
    private String channelId;
    private MessageWriteType writerType;
    private String message;
    private Date created;
    private Date updated;
}