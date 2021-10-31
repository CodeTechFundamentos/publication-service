package com.nutrix.command.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    private Integer id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private Date createdAt;
}
