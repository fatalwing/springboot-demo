package com.townmc.boot.web;

import com.townmc.utils.JsonUtil;
import com.townmc.utils.LogicException;
import com.townmc.utils.Result;
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
import javax.servlet.http.HttpServletResponse;

/**
 * @author meng
 */
@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String errorHandler(HttpServletRequest req, Exception e) {

        String errorCode = "system_error";
        String errorInfo = "系统异常";
        if(e instanceof LogicException) {
            LogicException le = (LogicException) e;
            errorCode = le.getErrorCode();
            errorInfo = le.getErrorInfo();
        } else if( e instanceof IllegalArgumentException || e instanceof InvalidDataAccessApiUsageException ||
                e instanceof HttpMessageNotReadableException || e instanceof MissingServletRequestParameterException) {
            errorCode = "Illegal_argument";
            errorInfo = "请检查访问参数。";
            log.warn(errorCode, e);
        } else if (req.getServletPath().endsWith(".json") && e instanceof HttpMediaTypeNotSupportedException) {
            errorCode = "un_support_content_type";
            errorInfo = "请求类型只支持application/json";
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            errorCode = "un_support_method";
            errorInfo = "不支持的http方法";
        } else {
            log.error("not logic exception", e);
        }

        String contentType = req.getHeader("Content-Type");
        log.info("====== {}", req.getRequestURI());
        if(null != contentType && contentType.contains("text")) {
            return "<!DOCTYPE html><html><body>" + errorCode + ":" + errorInfo + "</body></html>";
        } else {
            return JsonUtil.object2Json(Result.fail(errorCode, errorInfo));
        }

    }
}
