package com.achilles.server.common.interceptor;

import com.achilles.common.constans.CommonConstant;
import com.achilles.exception.BizException;
import com.achilles.model.response.code.UserResultCode;
import com.achilles.server.dao.cache.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Slf4j
@Controller
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Value("${if.verify.login:true}")
    Boolean verifyLogin;

    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!verifyLogin){
            return true;
        }

        String servletPath = request.getServletPath();
        log.debug("--------------------------servletPath : {}",servletPath);

        // 404
        if (handler instanceof ResourceHttpRequestHandler) {
            log.debug("----------404--------handler : " + handler);
            return true;
        }

        String token;
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }else{
            token = request.getHeader(CommonConstant.TOKEN);
        }
        log.debug("----------handler--------token:" + token);

        if(StringUtils.isBlank(token)){
            throw new BizException( UserResultCode.NOT_LOGIN);
        }
        // todo admin
//        if ("wild".equals(token)) {
//            return true;
//        }
        String userUuid = redisService.get(token);
        if (userUuid == null) {
            throw new BizException(UserResultCode.NOT_LOGIN);
        }

        log.info("userUuid : {}", userUuid);
        redisService.set(token,userUuid,60000);
        MDC.put(CommonConstant.USER_UUID, userUuid);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

//        int status = response.getStatus();
//        log.info("---------------postHandle----------status : {}",status);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        log.info("---------------afterCompletion--------");
        MDC.remove(CommonConstant.USER_UUID);

    }

}
