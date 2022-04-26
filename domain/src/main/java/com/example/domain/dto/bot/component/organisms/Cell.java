package com.example.domain.dto.bot.component.organisms;

import com.example.domain.dto.bot.component.atom.Image;
import com.example.domain.dto.bot.component.molecules.Button;
import com.example.domain.dto.bot.component.molecules.RichText;
import com.example.domain.dto.bot.component.molecules.Text;
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
public class Cell {
    private CellType type;
    private String classId;
    private Image image;
    private Button button;
    private Text text;
    private RichText richText;
}
