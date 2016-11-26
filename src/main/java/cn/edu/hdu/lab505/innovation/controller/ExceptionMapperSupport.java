package cn.edu.hdu.lab505.innovation.controller;

import cn.edu.hdu.lab505.innovation.service.Exception.SensorDataIndexOutOfBoundsException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import javax.security.auth.login.LoginException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapperSupport implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = Logger.getLogger(ExceptionMapperSupport.class);
    ;

    public Response toResponse(Exception exception) {
        String message = "Server Error";
        Status statusCode = Status.INTERNAL_SERVER_ERROR;
        // 处理unchecked exception
        if (exception instanceof LoginException) {
            statusCode = Status.UNAUTHORIZED;
            message = exception.getMessage();
        } else if (exception instanceof SensorDataIndexOutOfBoundsException) {
            statusCode = Status.BAD_REQUEST;
            message = exception.getMessage();
        } else if (exception instanceof DuplicateKeyException) {
            statusCode = Status.CONFLICT;
            message = exception.getMessage();
        } else if (exception instanceof NotFoundException) {
            statusCode = Status.NOT_FOUND;
            message = exception.getMessage();
        } else if (exception instanceof WebApplicationException) {
            WebApplicationException we = (WebApplicationException) exception;
            LOGGER.error(we.getMessage(), exception);
            return we.getResponse();
        }
        // checked exception和unchecked exception均被记录在日志里  
        LOGGER.error(message, exception);
        return Response.ok(message, MediaType.TEXT_PLAIN).status(statusCode).build();
    }
}
