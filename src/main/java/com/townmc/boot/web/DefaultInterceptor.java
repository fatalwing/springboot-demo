package com.townmc.boot.web;

import com.townmc.boot.utils.SpringUtil;
import com.townmc.boot.constants.SystemConstants;
import com.townmc.boot.utils.annotations.AccessToken;
import com.townmc.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class DefaultInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        AccessRequest accessRequest = new AccessRequest();
        accessRequest.setQueryString(httpServletRequest.getQueryString());
        accessRequest.setRequestURI(httpServletRequest.getRequestURI());
        accessRequest.setServletPath(httpServletRequest.getServletPath());
        accessRequest.setSessionId(httpServletRequest.getRequestedSessionId());

        httpServletRequest.setAttribute("accessRequest", accessRequest);

        log.debug("==== request:{}", JsonUtil.object2Json(accessRequest));

        // 检查conroller的方法是否存在AccessToken标注
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(AccessToken.class)) {
                String accessToken = httpServletRequest.getHeader(SystemConstants.AUTHORIZATION_KEY);

                AccessToken accessTokenAnnotation = handlerMethod.getMethodAnnotation(AccessToken.class);

                String name = accessTokenAnnotation.value();
                TokenHandler tokenHandler = (TokenHandler) SpringUtil.getBean(name);
                tokenHandler.getObjectByToken(accessToken, accessRequest);

                String[] tags = accessTokenAnnotation.tags();

                for (String tag : tags) {
                    // 额外校验

                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
