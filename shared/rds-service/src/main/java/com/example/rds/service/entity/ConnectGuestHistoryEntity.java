package com.example.rds.service.entity;

import com.example.domain.type.ConnectServerStatus;
import com.example.domain.type.ConnectTargetId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("connect_guest_history")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ConnectGuestHistoryEntity {

    @Id
    private Long historyNo;
    private Integer serviceNo;
    private String cid;
    private String targetId;
    private ConnectTargetId targetConnectId;
    private ConnectServerStatus status;
    private Date created;
}