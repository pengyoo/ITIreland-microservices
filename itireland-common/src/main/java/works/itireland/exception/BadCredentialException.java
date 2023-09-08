package works.itireland.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid username or password")
public class BadCredentialException extends RuntimeException{
    public BadCredentialException(String message) {
        super(message);
    }
}
