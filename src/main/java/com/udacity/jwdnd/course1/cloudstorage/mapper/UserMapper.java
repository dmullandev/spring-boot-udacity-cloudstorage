package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Mapper
public interface UserMapper {
	@Select("SELECT * FROM USERS WHERE username = #{username}")
	User getUser(String username);

	@Insert("INSERT INTO USERS (userid, username, salt, password, firstname, lastname) VALUES(#{userid}, #{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
	@Options(useGeneratedKeys = true, keyProperty = "userid")
	Integer insertUser(User user);

	@Delete("DELETE FROM USERS WHERE userid = #{userId}")
	void deleteUser(Integer userId);
}