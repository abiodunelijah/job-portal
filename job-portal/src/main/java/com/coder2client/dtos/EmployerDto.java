package com.coder2client.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String companyName;
    private String email;
}