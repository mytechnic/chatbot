package com.example.domain.rest.bot;

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
public class CurrentBotMessageListByCidRequest {

    @Schema(description = "서비스 ID", example = "star-chatbot", minLength = 1, maxLength = 60)
    @JsonDeserialize(using = TrimJsonDeserializer.class)
    @NotEmpty
    @Length(min = 1, max = 60)
    private String serviceId;

    @Schema(description = "내부용 고객 식별 ID", example = "CID:bfc786b078dc4e2990dd391588e26c38")
    @NotEmpty
    @JsonDeserialize(using = TrimJsonDeserializer.class)
    private String cid;

    @Schema(description = "마지막으로 수신한 메시지 일련번호")
    private Long latestMessageNo;
}
