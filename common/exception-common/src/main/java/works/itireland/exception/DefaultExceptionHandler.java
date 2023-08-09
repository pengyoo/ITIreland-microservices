package works.itireland.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(ResourceNotFoundException e,
                                                                HttpServletRequest request) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(ApiRequestException e,
                                                                HttpServletRequest request) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(ValidationException e,
                                                                HttpServletRequest request) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(IllegalAccessException e,
                                                                HttpServletRequest request) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleException(Exception e,
                                                    HttpServletRequest request) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
