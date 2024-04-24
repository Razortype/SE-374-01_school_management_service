package com.vsproject.VisualProgrammingBackend.api.fake;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FLoginDto {

    private String email;
    private String password;

}