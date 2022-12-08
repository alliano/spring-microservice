package com.email.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EmailDto {

    private String to;

    private String subject;

    private String body;
}
