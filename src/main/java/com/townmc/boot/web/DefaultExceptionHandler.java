package com.townmc.boot.web;

import com.townmc.boot.domain.enums.Err;
import com.townmc.boot.utils.BrokenException;
import com.townmc.boot.utils.Result;
import com.townmc.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author meng
 */
@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String errorHandler(HttpServletRequest req, Exception e) {

        Err err = Err.SYSTEM_ERROR;
        if(e instanceof BrokenException) {
            BrokenException le = (BrokenException) e;
            err = le.getErr();
        } else if( e instanceof IllegalArgumentException || e instanceof InvalidDataAccessApiUsageException ||
                e instanceof HttpMessageNotReadableException || e instanceof MissingServletRequestParameterException) {
            err = Err.PARAMETER_ERROR;
        } else if (e instanceof HttpMediaTypeNotSupportedException || e instanceof HttpRequestMethodNotSupportedException) {
            err = Err.UNSUPPORT_REQUEST;
        } else {
            log.error("not logic exception", e);
        }

        String contentType = req.getHeader("Content-Type");
        log.info("====== {}", req.getRequestURI());
        if(null != contentType && contentType.contains("text")) {
            return "<!DOCTYPE html><html><body>" + err.getError() + ":" + err.getMessage() + "</body></html>";
        } else {
            return JsonUtil.object2Json(Result.fail(err));
        }

    }
}
