package com.site.blog.my.core.controller.blog;

import cn.hutool.captcha.ShearCaptcha;
import com.site.blog.my.core.controller.vo.BlogDetailVO;
import com.site.blog.my.core.dao.UserMapper;
import com.site.blog.my.core.entity.BlogComment;
import com.site.blog.my.core.entity.BlogLink;
import com.site.blog.my.core.entity.User;
import com.site.blog.my.core.service.*;
import com.site.blog.my.core.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
public class MyBlogController {

//    public static String theme = "default";
//    public static String theme = "yummy-jekyll";
    public static String theme = "amaze";
    @Resource
    private BlogService blogService;
    @Resource
    private TagService tagService;
    @Resource
    private LinkService linkService;
    @Resource
    private CommentService commentService;
    @Resource
    private ConfigService configService;
    @Resource
    private CategoryService categoryService;

    @Resource
    private UserMapper userMapper;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping({"/", "/index", "index.html"})
    public String index(HttpServletRequest request) {
        return this.page(request, 1);
    }

    /**
     * 首页 分页数据
     *
     * @return
     */
    @GetMapping({"/page/{pageNum}"})
    public String page(HttpServletRequest request, @PathVariable("pageNum") int pageNum) {
        PageResult blogPageResult = blogService.getBlogsForIndexPage(pageNum);
        if (blogPageResult == null) {
            return "error/error_404";
        }
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("categories",categoryService.selectCategoriesRank());
        request.setAttribute("pageName", "首页");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/index";
    }

    /**
     * Categories页面(包括分类数据和标签数据)
     *
     * @return
     */
    @GetMapping({"/categories"})
    public String categories(HttpServletRequest request) {
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("categories", categoryService.selectCategoriesRank());
        request.setAttribute("pageName", "分类页面");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/category";
    }

    /**
     * 详情页
     *
     * @return
     */
    @GetMapping({"/blog/{blogId}", "/article/{blogId}"})
    public String detail(HttpServletRequest request, @PathVariable("blogId") Long blogId, @RequestParam(value = "commentPage", required = false, defaultValue = "1") Integer commentPage) {
        BlogDetailVO blogDetailVO = blogService.getBlogDetail(blogId);
        String user=(String) request.getSession().getAttribute("user");
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
            request.setAttribute("commentPageResult", commentService.getCommentPageByBlogIdAndPageNum(blogId, commentPage));
        }
        request.setAttribute("pageName", "详情");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/detail";
    }

    /**
     * 标签列表页
     *
     * @return
     */
    @GetMapping({"/tag/{tagName}"})
    public String tag(HttpServletRequest request, @PathVariable("tagName") String tagName) {
        return tag(request, tagName, 1);
    }

    /**
     * 标签列表页
     *
     * @return
     */
    @GetMapping({"/tag/{tagName}/{page}"})
    public String tag(HttpServletRequest request, @PathVariable("tagName") String tagName, @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageByTag(tagName, page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "标签");
        request.setAttribute("pageUrl", "tag");
        request.setAttribute("keyword", tagName);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("categories",categoryService.selectCategoriesRank());
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/list";
    }

    /**
     * 分类列表页
     *
     * @return
     */
    @GetMapping({"/category/{categoryName}"})
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName) {
        return category(request, categoryName, 1);
    }

    /**
     * 分类列表页
     *
     * @return
     */
    @GetMapping({"/category/{categoryName}/{page}"})
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName, @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageByCategory(categoryName, page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "分类");
        request.setAttribute("pageUrl", "category");
        request.setAttribute("keyword", categoryName);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("categories",categoryService.selectCategoriesRank());
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/list";
    }

    /**
     * 搜索列表页
     *
     * @return
     */
    @GetMapping({"/search/{keyword}"})
    public String search(HttpServletRequest request, @PathVariable("keyword") String keyword) {
        return search(request, keyword, 1);
    }

    /**
     * 搜索列表页
     *
     * @return
     */
    @GetMapping({"/search/{keyword}/{page}"})
    public String search(HttpServletRequest request, @PathVariable("keyword") String keyword, @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageBySearch(keyword, page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "搜索");
        request.setAttribute("pageUrl", "search");
        request.setAttribute("keyword", keyword);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("categories",categoryService.selectCategoriesRank());
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/list";
    }


    /**
     * 推荐网站
     *
     * @return
     */
    @GetMapping({"/link"})
    public String link(HttpServletRequest request) {
        request.setAttribute("pageName", "推荐网站");
        Map<Byte, List<BlogLink>> linkMap = linkService.getLinksForLinkPage();
        if (linkMap != null) {
            //判断类别并封装数据 0-友链 1-交流学习网站 2-学习知识网站 3-开发工具网站
            if (linkMap.containsKey((byte) 0)) {
                request.setAttribute("favoriteLinks", linkMap.get((byte) 0));
            }
            if (linkMap.containsKey((byte) 1)) {
                request.setAttribute("recommendLinks", linkMap.get((byte) 1));
            }
            if (linkMap.containsKey((byte) 2)) {
                request.setAttribute("learningLinks", linkMap.get((byte) 2));
            }
            if(linkMap.containsKey((byte) 3)){
                request.setAttribute("toolLinks",linkMap.get((byte) 3));
            }
        }
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/link";
    }

    @GetMapping({"/project"})
    public String project(HttpServletRequest request) {
        request.setAttribute("pageName", "个人项目");
        Map<Byte, List<BlogLink>> linkMap = linkService.getLinksForLinkPage();
        if (linkMap != null) {
            //判断类别并封装数据 8-个人项目
            if(linkMap.containsKey((byte) 8)){
                request.setAttribute("projectLinks",linkMap.get((byte) 8));
            }
        }
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/project";
    }

    @GetMapping({"/tools"})
    public String tools(HttpServletRequest request) {
        request.setAttribute("pageName", "应用工具");
        Map<Byte, List<BlogLink>> linkMap = linkService.getLinksForLinkPage();
        if (linkMap != null) {
            //判断类别并封装数据 9-个人项目
            if(linkMap.containsKey((byte) 9)){
                request.setAttribute("projectLinks",linkMap.get((byte) 9));
            }
        }
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/tools";
    }


    /**
     * 评论操作
     */
    @PostMapping(value = "/blog/comment")
    @ResponseBody
    public Result comment(HttpServletRequest request, HttpSession session,
                          @RequestParam Long blogId, @RequestParam String verifyCode,
                          @RequestParam String commentator, @RequestParam String email,
                          @RequestParam String websiteUrl, @RequestParam String commentBody) {
                if (!StringUtils.hasText(verifyCode)) {
                    return ResultGenerator.genFailResult("验证码不能为空");
                }
                ShearCaptcha shearCaptcha = (ShearCaptcha) session.getAttribute("verifyCode");
                if (shearCaptcha == null || !shearCaptcha.verify(verifyCode)) {
                    return ResultGenerator.genFailResult("验证码错误");
                }
                String ref = request.getHeader("Referer");
                if (!StringUtils.hasText(ref)) {
                    return ResultGenerator.genFailResult("非法请求");
                }
                if (null == blogId || blogId < 0) {
                    return ResultGenerator.genFailResult("非法请求");
                }
                if (!StringUtils.hasText(commentator)) {
                    return ResultGenerator.genFailResult("请输入称呼");
                }
                if (!StringUtils.hasText(email)) {
                    return ResultGenerator.genFailResult("请输入邮箱地址");
                }
                if (!PatternUtil.isEmail(email)) {
                    return ResultGenerator.genFailResult("请输入正确的邮箱地址");
                }

                if (!StringUtils.hasText(commentBody)) {
                    return ResultGenerator.genFailResult("请输入评论内容");
                }
                if (commentBody.trim().length() > 200) {
                    return ResultGenerator.genFailResult("评论内容过长");
                }

        BlogComment comment = new BlogComment();
        comment.setBlogId(blogId);
        comment.setCommentator(MyBlogUtils.cleanString(commentator));
        comment.setEmail(email);
        if (PatternUtil.isURL(websiteUrl)) {
            comment.setWebsiteUrl(websiteUrl);
        }
        comment.setCommentBody(MyBlogUtils.cleanString(commentBody));
        return ResultGenerator.genSuccessResult(commentService.addComment(comment));
    }





    /**
     * 关于页面 以及其他配置了subUrl的文章页
     *
     * @return
     */
    @GetMapping({"/{subUrl}"})
    public String detail(HttpServletRequest request, @PathVariable("subUrl") String subUrl) {
        BlogDetailVO blogDetailVO = blogService.getBlogDetailBySubUrl(subUrl);
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
            request.setAttribute("pageName", subUrl);
            request.setAttribute("configurations", configService.getAllConfigs());
            return "blog/" + theme + "/detail";
        } else {
            return "error/error_400";
        }
    }

    @GetMapping({"/about"})
    public String about(HttpServletRequest request) {

        BlogDetailVO blogDetailVO = blogService.getBlogDetailBySubUrl("about");
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
            request.setAttribute("pageName", "关于作者");
            request.setAttribute("configurations", configService.getAllConfigs());
            return "blog/" + theme + "/about";
        } else {
            return "error/error_400";
        }
    }
    @GetMapping({"/aboutBlog"})
    public String aboutBlog(HttpServletRequest request) {

        BlogDetailVO blogDetailVO = blogService.getBlogDetailBySubUrl("blog");
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
            request.setAttribute("pageName", "关于博客");
            request.setAttribute("configurations", configService.getAllConfigs());
            return "blog/" + theme + "/about-blog";
        } else {
            return "error/error_400";
        }
    }


}
