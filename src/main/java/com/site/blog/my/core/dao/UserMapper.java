package com.site.blog.my.core.dao;

import com.site.blog.my.core.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Insert("INSERT INTO tb_user (commentator, email) VALUES (#{commentator}, #{email});")
    void addUser(String commentator,String email,String websiteUrl);

    @Select("select * from  tb_user")
    User findUser(String email);
}
