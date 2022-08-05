package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredential(String credentialid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    Integer insertCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void deleteCredential(Integer credentialid);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password}, userid = #{userid}, key = #{key} WHERE credentialid = #{credentialid}")
    public int updateCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getAllCredentials(Integer userid);

}
