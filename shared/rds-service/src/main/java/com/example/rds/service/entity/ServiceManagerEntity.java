package com.example.rds.service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("service_manager")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ServiceManagerEntity {

    @Id
    private Integer serviceNo;
    private String serviceId;
    private String name;
    private String description;
    private Date created;
    private Date updated;
}