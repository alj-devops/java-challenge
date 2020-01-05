package jp.co.axa.apidemo.controllers.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Adding the Default Controller Error Handler.
 *
 * @author li.han
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * DefaultErrorHandler for overall exception handler.
     *
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     * @param exception Exception
     * @return null
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest request, HttpServletResponse response,
            Exception exception) {
        try {
            log.warn("Unexpected error occurred. " + exception.getMessage());
            String result = "Error Happend, Please Contact with Administrator";
            response.getWriter().write(result);
            return null;
        } catch (Exception e) {
            log.error("Error handling function is failed. " + e.getMessage());
            return null;
        }
    }
}
