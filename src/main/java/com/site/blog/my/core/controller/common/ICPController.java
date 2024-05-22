package com.site.blog.my.core.controller.common;

import com.site.blog.my.core.service.ConfigService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ICPController {
    @Resource
    private ConfigService configService;

    @GetMapping("/icp")
    public String getICP(){
        Map<String, String> allConfigs = configService.getAllConfigs();
        return allConfigs.getOrDefault("footerICP", "暂无ICP");
    }
}
