package com.example.rds.service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("connect_user")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ConnectUserEntity {

    @Id
    private Long userNo;
    private Integer serviceNo;
    private String connectId;
    private String uid;
    private String cid;
    private Date created;
    private Date updated;
}