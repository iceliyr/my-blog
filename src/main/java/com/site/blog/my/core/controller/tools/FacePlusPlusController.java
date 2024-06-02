package com.site.blog.my.core.controller.tools;

import com.site.blog.my.core.service.ConfigService;
import com.site.blog.my.core.util.FacePlusPlusUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FacePlusPlusController {  
  
    @Resource
    private FacePlusPlusUtil facePlusPlusUtil;

    @Resource
    private ConfigService configService;

    @RequestMapping("/matting")
    public String matting(HttpServletRequest request){
        request.setAttribute("configurations", configService.getAllConfigs());
        return "tools/matting";
    }

    @RequestMapping("/matting/upload")
    @ResponseBody // 告诉Spring MVC返回JSON而不是视图名称
    public ResponseEntity<Map<String, String>> segmentImage(
            @RequestParam("image") MultipartFile file,
            @RequestParam(value = "returnGrayscale", required = false, defaultValue = "1") Integer returnGrayscale) throws IOException {
        String imagePath = facePlusPlusUtil.segmentImage(file, returnGrayscale);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("imageResult", imagePath);

        return ResponseEntity.ok(responseMap);
    }

}