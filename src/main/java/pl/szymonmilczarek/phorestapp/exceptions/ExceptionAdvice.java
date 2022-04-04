package pl.szymonmilczarek.phorestapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.szymonmilczarek.phorestapp.model.BaseError;

@ControllerAdvice
public class ExceptionAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ResponseBody
    @ExceptionHandler({EntityDoesNotExistException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseError handleNotFoundErrors(Exception ex) {
        LOG.error("{}", ex.getMessage());
        return new BaseError(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({NotEnoughMoneyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseError handleBadRequestErrors(Exception ex) {
        LOG.error("{}", ex.getMessage());
        return new BaseError(ex.getMessage());
    }
}
