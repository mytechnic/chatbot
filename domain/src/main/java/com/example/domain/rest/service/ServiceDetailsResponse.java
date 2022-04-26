package com.example.domain.rest.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Schema
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ServiceDetailsResponse {

    @Schema(description = "서비스 ID", example = "example-chatbot")
    private String serviceId;

    @Schema(description = "서비스 이름", example = "Example 서비스")
    private String name;

    @Schema(description = "설명", example = "Example Description")
    private String description;

    @Schema(description = "수정시간", example = "1650032823000")
    private Date updated;

    @Schema(description = "생성시간", example = "1650032823000")
    private Date created;
}
