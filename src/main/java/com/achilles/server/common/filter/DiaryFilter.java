package com.achilles.server.common.filter;

import com.achilles.common.constans.CommonConstant;
import com.achilles.tool.generate.unique.GenerateUniqueUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.io.IOException;


@WebFilter(filterName = "diaryFilter", urlPatterns = "/*")
@Slf4j
public class DiaryFilter implements Filter {


    @Value("${spring.profiles.active}")
    String env;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        long startTime = System.currentTimeMillis();

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceId = request.getHeader(CommonConstant.TRACE_ID);
        if(StringUtils.hasLength(traceId)){
            traceId = GenerateUniqueUtil.getUuId();
        }
        log.debug("traceId : " + traceId);

        MDC.put(CommonConstant.TRACE_ID, traceId);

        String servletPath = request.getServletPath();

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if ("dev".equals(env)) {
            response.setHeader("Access-Control-Allow-Origin", "*"); // 用nginx 时屏蔽
        }
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "token, Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");


        String method = request.getMethod();
        if (method.equalsIgnoreCase("OPTIONS")) {
            servletResponse.getOutputStream().write("Success".getBytes("utf-8"));
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        long duration = System.currentTimeMillis() - startTime;
        log.info(servletPath + " (" + request.getMethod() + ") -> " + duration+"ms");

        MDC.remove(CommonConstant.TRACE_ID);
    }

    @Override
    public void destroy() {
    }
}
