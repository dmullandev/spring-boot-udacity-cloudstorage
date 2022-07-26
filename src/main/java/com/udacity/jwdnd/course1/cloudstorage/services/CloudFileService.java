package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CloudFileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CloudFile;

@Service
public class CloudFileService {

	private CloudFileMapper cloudFileMapper;
	private UserService userService;

	public CloudFileService(CloudFileMapper cloudFileMapper, UserService userService) {
		this.cloudFileMapper = cloudFileMapper;
		this.userService = userService;
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("Creating CloudFileService bean");
	}

	public void addCloudFile(String username, CloudFile cloudFile) {

	}

	/**
	 * 
	 * @param username the username to get all files for
	 * @return the list of all uploaded {@link CloudFile}s for {@link User}
	 */
	public List<CloudFile> getCloudFiles(String username) {
		return cloudFileMapper.getAllCloudFiles(userService.getUser(username).getUserid());
	}

}
