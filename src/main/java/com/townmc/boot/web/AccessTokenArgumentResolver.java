package com.townmc.boot.web;

import com.townmc.boot.domain.enums.Handler;
import com.townmc.boot.utils.annotations.AccessToken;
import com.townmc.boot.constants.SystemConstants;
import com.townmc.boot.utils.LogicException;
import com.townmc.utils.JsonUtil;
import com.townmc.boot.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

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
            throw new LogicException("token_handler_not_exists", "名字为" + name + "的tokenHandler没有实现，请联系客服");
        }

        log.debug("===== accessRequest: " + JsonUtil.object2Json(nativeWebRequest.getAttribute("accessRequest", 0)));
        return handler.getObjectByToken(accessToken, (AccessRequest) nativeWebRequest.getAttribute("accessRequest", 0));
    }
}
