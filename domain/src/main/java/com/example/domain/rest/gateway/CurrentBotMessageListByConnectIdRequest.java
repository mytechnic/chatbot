package com.example.domain.rest.gateway;

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
public class CurrentBotMessageListByConnectIdRequest {

    @Schema(description = "서비스 ID", example = "star-chatbot", minLength = 1, maxLength = 60)
    @JsonDeserialize(using = TrimJsonDeserializer.class)
    @NotEmpty
    @Length(min = 1, max = 60)
    private String serviceId;

    @Schema(description = "클라이언트용 고객 식별 ID", example = "CON:b200f53a6e0e497caf4c6e84b4b5677e")
    @JsonDeserialize(using = TrimJsonDeserializer.class)
    @NotEmpty
    private String connectId;

    @Schema(description = "마지막으로 수신한 메시지 일련번호")
    private Long latestMessageNo;
}
