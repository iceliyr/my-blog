package com.site.blog.my.core.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@Component
public class FacePlusPlusUtil {
    @Value("${images.path}")
    private String imagePath;

    @Value("${faceplusplus.api_key}")  
    private String apiKey;
  
    @Value("${faceplusplus.api_secret}")  
    private String apiSecret;
  
    private static final String ENDPOINT = "https://api-cn.faceplusplus.com/humanbodypp/v2/segment";  
  
    public String segmentImage(MultipartFile file, Integer returnGrayscale) throws IOException {
        RestTemplate restTemplate = new RestTemplate();  
  
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();  
        bodyMap.add("api_key", apiKey);  
        bodyMap.add("api_secret", apiSecret);  
        bodyMap.add("image_file", new ByteArrayResource(file.getBytes()) {
            @Override  
            public String getFilename() {  
                return file.getOriginalFilename();  
            }  

            public String getContentType() {  
                return MediaType.IMAGE_JPEG_VALUE; // 根据实际情况设置图片类型  
            }  
        });  
        if (returnGrayscale != null) {  
            bodyMap.add("return_grayscale", returnGrayscale);  
        }  
  
        HttpHeaders headers = new HttpHeaders();  
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);  
  
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);  
  
        Map<String, Object> result = restTemplate.postForObject(ENDPOINT, requestEntity, Map.class);

        String base64ImageString = null; // 替换为你的Base64字符串
        if (result.containsKey("body_image")) {
            base64ImageString= (String) result.get("body_image");
        }

        // 解码Base64字符串
        byte[] imageBytes = Base64.decodeBase64(base64ImageString);

        // 指定输出图片文件的路径和名称

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();

        Path path = Paths.get(imagePath+newFileName);
        try {
            // 写入文件
            Files.write(path, imageBytes);
            System.out.println("图片已成功转换为文件: " + path.toAbsolutePath());
            return base64ImageString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }  
  
    // ByteArrayResource是一个简单的资源实现，用于包装字节数组  
    // 如果你的Spring版本中没有这个类，你可能需要自定义一个类似的类  
}