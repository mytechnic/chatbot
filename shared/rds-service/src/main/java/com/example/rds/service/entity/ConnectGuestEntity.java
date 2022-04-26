package com.example.rds.service.entity;

import com.example.domain.type.Channel;
import com.example.domain.type.ConnectServerStatus;
import com.example.domain.type.ConnectTargetId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("connect_guest")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ConnectGuestEntity {

    private Integer serviceNo;
    private String connectId;
    private String cid;
    private Channel connectChannel;
    private ConnectTargetId targetId;
    private String targetConnectId;
    private ConnectServerStatus status;
    private Date created;
    private Date updated;
}