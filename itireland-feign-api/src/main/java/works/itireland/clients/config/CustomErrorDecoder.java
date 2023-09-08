package works.itireland.clients.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import works.itireland.exception.BadCredentialException;
import works.itireland.exception.ResourceNotFoundException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                return new BadCredentialException("Invalid username or password");
            case 404:
                return new ResourceNotFoundException("Resource not found");
            case 503:
                return new RuntimeException("Api is unavailable");
            default:
                return new Exception("Exception");
        }
    }
}