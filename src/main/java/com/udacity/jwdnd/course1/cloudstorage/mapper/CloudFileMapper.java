package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudFile;

@Mapper
public interface CloudFileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND filename = #{cloudFilename}")
    CloudFile getCloudFile(Integer userid, String cloudFilename);

    @Select("SELECT * FROM FILES WHERE filename = #{cloudFilename}")
    CloudFile viewCloudFile(String cloudFilename);

    @Select("SELECT * FROM FILES WHERE filename = #{cloudFilename}")
    Integer getCloudFileId(String cloudFilename);

    @Insert("INSERT INTO FILES (fileId, filename, contenttype, filesize, userid, filedata) VALUES(#{fileId}, #{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertCloudFile(CloudFile cloudFile);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteCloudFile(Integer filename);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<CloudFile> getAllCloudFiles(Integer userid);
}
