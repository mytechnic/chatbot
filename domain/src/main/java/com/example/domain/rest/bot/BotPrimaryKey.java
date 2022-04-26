package com.example.domain.rest.bot;

import com.example.core.databind.TrimJsonDeserializer;
import com.example.domain.type.Channel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BotPrimaryKey {

    @Schema(description = "서비스 ID", minLength = 1, maxLength = 60)
    @JsonDeserialize(using = TrimJsonDeserializer.class)
    @NotEmpty
    @Length(min = 1, max = 60)
    private String serviceId;

    @Schema(description = "인입 채널")
    @NotNull
    private Channel channel;

    @Schema(description = "인입 채널 ID")
    @JsonDeserialize(using = TrimJsonDeserializer.class)
    private String channelId;

    @Schema(description = "내부용 고객 식별 ID")
    @NotEmpty
    @JsonDeserialize(using = TrimJsonDeserializer.class)
    private String cid;
}
