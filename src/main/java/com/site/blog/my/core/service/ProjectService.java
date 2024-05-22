package com.site.blog.my.core.service;

import com.site.blog.my.core.entity.Project;
import com.site.blog.my.core.util.PageQueryUtil;
import com.site.blog.my.core.util.PageResult;

import java.util.List;
import java.util.Map;

public interface ProjectService {
    /**
     * 查询项目的分页数据
     *
     * @param pageUtil
     * @return
     */
    PageResult getProjectPage(PageQueryUtil pageUtil);

    int getTotalProjects();

    Boolean saveProject(Project project);

    Project selectById(Integer id);

    Boolean updateProject(Project tempProject);

    Boolean deleteBatch(Integer[] ids);

    /**
     * 返回项目页面所需的所有数据
     *
     * @return
     */
    Map<Byte, List<Project>> getProjectsForProjectPage();
}
