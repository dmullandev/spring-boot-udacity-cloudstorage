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

	@Select("SELECT * FROM FILES WHERE fileId = #{cloudFileId}")
	CloudFile getCloudFile(String cloudFileId);

	@Select("SELECT * FROM FILES WHERE userid = #{userid}")
	List<CloudFile> getAllCloudFiles(Integer userid);

	@Insert("INSERT INTO FILES (fileId, filename, contenttype, filesize, userid, filedata) VALUES(#{fileId}, #{fileName}), #{contentType}), #{fileSize}), #{userId}), #{fileData})")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	Integer insertCloudFile(CloudFile cloudFile);

	@Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
	void deleteCloudFile(Integer cloudFileId);
}
