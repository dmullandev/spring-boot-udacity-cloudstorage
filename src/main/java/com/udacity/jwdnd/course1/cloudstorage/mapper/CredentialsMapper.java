package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Mapper
public interface CredentialsMapper {

	@Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
	Credential getCredential(String credentialId);

	@Insert("INSERT INTO CREDENTIALS (credentialid, url, username, key, password, userid) VALUES(#{credentialId}, #{url}), #{username}), #{key}), #{password}), #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	Integer insertCredential(Credential credential);

	@Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
	void deleteCredential(Integer credentialId);

}
