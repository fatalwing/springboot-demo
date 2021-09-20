package com.townmc.boot.web;

import com.townmc.boot.model.enums.Err;
import com.townmc.boot.model.enums.Handler;
import com.townmc.boot.utils.BrokenException;
import com.townmc.boot.utils.annotations.AccessToken;
import com.townmc.boot.constants.SystemConstants;
import com.townmc.boot.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author meng
 */
@Slf4j
public class AccessTokenArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        return methodParameter.hasParameterAnnotation(AccessToken.class);
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, @Nullable WebDataBinderFactory webDataBinderFactory) throws Exception {

        String accessToken = nativeWebRequest.getHeader(SystemConstants.AUTHORIZATION_KEY);
        AccessToken tokenAnno = methodParameter.getParameterAnnotation(AccessToken.class);
        Handler name = tokenAnno.value();
        TokenHandler handler = (TokenHandler) SpringUtil.getBean(name.toString());

        if (null == handler) {
            throw new BrokenException(Err.APPLICATION_ERROR);
        }

        return handler.getObjectByToken(accessToken, nativeWebRequest);
    }
}
