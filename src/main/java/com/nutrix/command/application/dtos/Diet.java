package com.nutrix.command.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diet {

    private Integer id;

    private String name;

    private String description;
}
