package com.site.blog.my.core.interceptor;

import com.site.blog.my.core.service.ConfigService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 后台系统身份验证拦截器
 */
@Component
public class ICPInterceptor implements HandlerInterceptor {

    @Resource
    private ConfigService configService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Map<String, String> allConfigs = configService.getAllConfigs();
        String icp= allConfigs.getOrDefault("footerICP", "暂无ICP");
        request.setAttribute("icp",icp);
        return true;
    }


}
