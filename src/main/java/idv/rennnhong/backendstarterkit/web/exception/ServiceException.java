package idv.rennnhong.backendstarterkit.web.exception;

import idv.rennnhong.common.response.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ServiceException extends RuntimeException {

    public ServiceException(ErrorMessages errorMessages) {
        super(errorMessages.getErrorMessage());
    }
}
