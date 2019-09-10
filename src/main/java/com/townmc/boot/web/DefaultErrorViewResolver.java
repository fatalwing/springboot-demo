package com.townmc.boot.web;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class DefaultErrorViewResolver implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request,
                                         HttpStatus status, Map<String, Object> model) {

        ModelAndView mav = new ModelAndView();
        String jsonFlag = ((String)model.get("path")).endsWith(".json") ? "_json" : "";
        if(status.value() == 404) {
            mav.setViewName("/error/404" + jsonFlag);
        }
        return mav;
    }

}
