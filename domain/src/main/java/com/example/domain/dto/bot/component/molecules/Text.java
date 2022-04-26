package com.example.domain.dto.bot.component.molecules;

import com.example.domain.dto.bot.component.atom.TextStyle;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Text {
    private String message;
    private TextStyle style;
}
