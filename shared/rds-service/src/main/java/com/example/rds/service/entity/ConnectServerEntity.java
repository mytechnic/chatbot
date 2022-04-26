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

@Table("connect_server")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ConnectServerEntity {

    @Id
    private Long connectNo;
    private Long userNo;
    private Channel channel;
    private String channelId;
    private ConnectTargetId targetId;
    private String targetConnectId;
    private ConnectServerStatus status;
    private Date created;
    private Date updated;
}