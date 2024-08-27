package com.ajaz.userservice.models;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApiResponse {
    private String message;
    private Boolean success;
    private HttpStatus httpStatus;
}
