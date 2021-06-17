package by.bip.site.controller.exception;

import by.bip.site.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@ControllerAdvice
public class CustomExceptionHandler extends ExceptionHandlerExceptionResolver {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse("400", "entity doesn't exist"), HttpStatus.BAD_REQUEST);
    }
}
