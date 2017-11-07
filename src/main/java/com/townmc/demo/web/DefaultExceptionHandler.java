package com.townmc.demo.web;

import com.townmc.demo.utils.ApiResponse;
import com.townmc.demo.utils.LogicException;
import com.townmc.utils.JsonUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String errorHandler(HttpServletRequest req, Exception e) {

        String errorCode = "system_error";
        String errorInfo = "系统异常";
        if(e instanceof LogicException) {
            LogicException le = (LogicException)e;
            errorCode = le.getErrorCode();
            errorInfo = le.getErrorInfo();
        }

        if(req.getServletPath().endsWith(".json")) {
            return JsonUtil.object2Json(ApiResponse.fail(errorCode, errorInfo));
        } else {
            return "<!DOCTYPE html><html><body>" + errorCode + ":" + errorInfo + "</body></html>";
        }

    }
}
