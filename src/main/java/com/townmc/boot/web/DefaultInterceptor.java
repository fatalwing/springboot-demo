package com.townmc.boot.web;

import com.townmc.boot.utils.EnumerationIter;
import com.townmc.boot.utils.SpringUtil;
import com.townmc.boot.constants.SystemConstants;
import com.townmc.boot.utils.annotations.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;

/**
 * @author meng
 */
@Slf4j
@Component
public class DefaultInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        // 检查conroller的方法是否存在AccessToken标注
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(AccessToken.class)) {
                String accessToken = httpServletRequest.getHeader(SystemConstants.AUTHORIZATION_KEY);

                AccessToken accessTokenAnnotation = handlerMethod.getMethodAnnotation(AccessToken.class);

                String name = accessTokenAnnotation.value().name();
                TokenHandler tokenHandler = (TokenHandler) SpringUtil.getBean(name);
                tokenHandler.getObjectByToken(accessToken, this.toNativeWebRequest(httpServletRequest, httpServletResponse));

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

    private NativeWebRequest toNativeWebRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new NativeWebRequest() {
            @Override
            public Object getNativeRequest() {
                return httpServletRequest;
            }

            @Override
            public Object getNativeResponse() {
                return httpServletResponse;
            }

            @Override
            public <T> T getNativeRequest(Class<T> aClass) {
                return null;
            }

            @Override
            public <T> T getNativeResponse(Class<T> aClass) {
                return null;
            }

            @Override
            public String getHeader(String s) {
                return httpServletRequest.getHeader(s);
            }

            @Override
            public String[] getHeaderValues(String s) {
                return httpServletRequest.getParameterValues(s);
            }

            @Override
            public Iterator<String> getHeaderNames() {
                return new EnumerationIter(httpServletRequest.getHeaderNames()).iterator();
            }

            @Override
            public String getParameter(String s) {
                return httpServletRequest.getParameter(s);
            }

            @Override
            public String[] getParameterValues(String s) {
                return httpServletRequest.getParameterValues(s);
            }

            @Override
            public Iterator<String> getParameterNames() {
                return new EnumerationIter(httpServletRequest.getParameterNames()).iterator();
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return httpServletRequest.getParameterMap();
            }

            @Override
            public Locale getLocale() {
                return httpServletRequest.getLocale();
            }

            @Override
            public String getContextPath() {
                return httpServletRequest.getContextPath();
            }

            @Override
            public String getRemoteUser() {
                return httpServletRequest.getRemoteUser();
            }

            @Override
            public Principal getUserPrincipal() {
                return httpServletRequest.getUserPrincipal();
            }

            @Override
            public boolean isUserInRole(String s) {
                return httpServletRequest.isUserInRole(s);
            }

            @Override
            public boolean isSecure() {
                return httpServletRequest.isSecure();
            }

            @Override
            public boolean checkNotModified(long l) {
                return false;
            }

            @Override
            public boolean checkNotModified(String s) {
                return false;
            }

            @Override
            public boolean checkNotModified(String s, long l) {
                return false;
            }

            @Override
            public String getDescription(boolean b) {
                return null;
            }

            @Override
            public Object getAttribute(String s, int i) {
                return httpServletRequest.getAttribute(s);
            }

            @Override
            public void setAttribute(String s, Object o, int i) {
                httpServletRequest.setAttribute(s, o);
            }

            @Override
            public void removeAttribute(String s, int i) {
                httpServletRequest.removeAttribute(s);
            }

            @Override
            public String[] getAttributeNames(int i) {
                Enumeration<String> enums = httpServletRequest.getAttributeNames();
                List<String> li = new ArrayList<>();
                while(enums.hasMoreElements()) {
                    li.add(enums.nextElement());
                }
                String[] re = new String[li.size()];
                return li.toArray(re);
            }

            @Override
            public void registerDestructionCallback(String s, Runnable runnable, int i) {

            }

            @Override
            public Object resolveReference(String s) {
                return null;
            }

            @Override
            public String getSessionId() {
                return httpServletRequest.getRequestedSessionId();
            }

            @Override
            public Object getSessionMutex() {
                return null;
            }
        };
    }
}
