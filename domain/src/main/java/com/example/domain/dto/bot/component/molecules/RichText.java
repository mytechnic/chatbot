package com.example.domain.dto.bot.component.molecules;

import com.example.domain.dto.bot.component.atom.SegmentType;
import com.example.domain.dto.bot.component.atom.TextStyle;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RichText {
    private List<Segment> segments;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Segment {
        private String message;
        private SegmentType type;
        private TextStyle style;
    }
}
