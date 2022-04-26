package com.example.domain.rest.service;

import com.example.core.databind.TrimJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Schema
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ServiceCreateRequest {

    @Schema(description = "서비스 ID", example = "example-chatbot", minLength = 1, maxLength = 60)
    @JsonDeserialize(using = TrimJsonDeserializer.class)
    @NotEmpty
    @Length(min = 1, max = 60)
    private String serviceId;

    @Schema(description = "서비스 이름", example = "Example 서비스", minLength = 1, maxLength = 100)
    @JsonDeserialize(using = TrimJsonDeserializer.class)
    @NotEmpty
    @Length(min = 1, max = 100)
    private String name;

    @Schema(description = "설명", example = "Example Description", maxLength = 2000)
    @JsonDeserialize(using = TrimJsonDeserializer.class)
    @Length(max = 2000)
    private String description;
}
