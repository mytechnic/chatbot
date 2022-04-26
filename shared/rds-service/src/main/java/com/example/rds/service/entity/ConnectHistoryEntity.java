package com.example.rds.service.entity;

import com.example.domain.type.Channel;
import com.example.domain.type.ConnectServerStatus;
import com.example.domain.type.ConnectTargetId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("connect_history")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ConnectHistoryEntity {

    @Id
    private Long historyNo;
    private Long connectNo;
    private Channel channel;
    private String channelId;
    private ConnectTargetId targetId;
    private String targetConnectId;
    private ConnectServerStatus status;
    private Date created;
}