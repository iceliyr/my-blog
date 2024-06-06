package com.site.blog.my.core.interceptor;

import com.site.blog.my.core.service.BlogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class IndexInterceptor implements HandlerInterceptor {
    @Resource
    private BlogService blogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        int view=blogService.getTotalView();
        request.setAttribute("view",view);
        return true;
    }

}
