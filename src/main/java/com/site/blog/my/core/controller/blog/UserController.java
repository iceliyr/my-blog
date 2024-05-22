package com.site.blog.my.core.controller.blog;

import cn.hutool.captcha.ShearCaptcha;
import com.site.blog.my.core.dao.UserMapper;
import com.site.blog.my.core.entity.AdminUser;
import com.site.blog.my.core.entity.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Resource
    private UserMapper userMapper;

//    @PostMapping(value = "/login")
//    public String login(@RequestParam("userName") String userName,
//                        @RequestParam("password") String password,
//                        @RequestParam("verifyCode") String verifyCode,
//                        HttpSession session) {
//        if (!StringUtils.hasText(verifyCode)) {
//            session.setAttribute("errorMsg", "验证码不能为空");
//            return "blog/amaze/login";
//        }
//        if (!StringUtils.hasText(userName) || !StringUtils.hasText(password)) {
//            session.setAttribute("errorMsg", "用户名或密码不能为空");
//            return "blog/amaze/login";
//        }
//        ShearCaptcha shearCaptcha = (ShearCaptcha) session.getAttribute("verifyCode");
//        if (shearCaptcha == null || !shearCaptcha.verify(verifyCode)) {
//            session.setAttribute("errorMsg", "验证码错误");
//            return "blog/amaze/login";
//        }
//       User user = userMapper.findUser(userName);
//        if (adminUser != null) {
//            session.setAttribute("loginUser", adminUser.getNickName());
//            session.setAttribute("loginUserId", adminUser.getAdminUserId());
//            //session过期时间设置为7200秒 即两小时
//            //session.setMaxInactiveInterval(60 * 60 * 2);
//            return "redirect:/admin/index";
//        } else {
//            session.setAttribute("errorMsg", "登陆失败");
//            return "blog/amaze/login";
//        }
//    }
}
