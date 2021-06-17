package by.bip.site.controller.exception;

import lombok.Data;

@Data
public class ExceptionResponse {
    private final String code;
    private final String message;
}
