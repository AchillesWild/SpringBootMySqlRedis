package com.achilles.server.common.exception;

import com.achilles.exception.BizException;
import com.achilles.model.response.BaseResult;
import com.achilles.model.response.code.BaseResultCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.achilles.server.controller")
@Order(0)
@Slf4j
public class GlobalControllerExceptionAdvice {

    @ExceptionHandler(value = BizException.class)
    public BaseResult bizExceptionHandler(BizException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        BaseResult baseResult = BaseResult.fail(e.getCode(),e.getMessage());
        return baseResult;
    }

    @ExceptionHandler(value = Exception.class)
    public BaseResult exceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        BaseResult baseResult = BaseResult.fail(BaseResultCode.EXCEPTION_TO_CLIENT);
        return baseResult;
    }

    @ExceptionHandler(value = Throwable.class)
    public BaseResult throwableHandler(HttpServletRequest req, Throwable e) {
        e.printStackTrace();
        log.error(e.getMessage());
        BaseResult baseResult = BaseResult.fail(BaseResultCode.EXCEPTION_TO_CLIENT);
        return baseResult;
    }

}
