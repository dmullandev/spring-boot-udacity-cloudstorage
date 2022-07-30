package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CloudFileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CloudFile;

@Service
public class StorageService {

    private static final Logger LOG = LogManager.getLogger(StorageService.class);

    private CloudFileMapper cloudFileMapper;
    private UserService userService;
    private NoteMapper noteMapper;
    private CredentialsMapper credentialsMapper;

    public StorageService(CloudFileMapper cloudFileMapper, NoteMapper noteMapper,
            CredentialsMapper credentialsMapper, UserService userService) {
        this.cloudFileMapper = cloudFileMapper;
        this.noteMapper = noteMapper;
        this.credentialsMapper = credentialsMapper;
        this.userService = userService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating FileHandlingService bean");
    }

    public Integer insertFile(String username, MultipartFile mpf) {
        LOG.info("Inserting CloudFile for user: " + username + "for filename: " + mpf.getName());

        try {
            if (cloudFileMapper.getCloudFile(userService.getUserid(username), mpf.getName()) == null) {

                return cloudFileMapper.insertCloudFile(new CloudFile(1, mpf.getName(), mpf.getContentType(),
                        String.valueOf(mpf.getSize()), userService.getUserid(username), mpf.getBytes()));
            } else {
                return -2;
            }

        } catch (IOException ioe) {
            LOG.error(ioe.getMessage());
            return -1;
        }
    }

    /**
     * 
     * @param username the username to get all files for
     * @return the list of all uploaded {@link CloudFile}s for {@link User}
     */
    public List<CloudFile> getCloudFiles(String username) {
        return cloudFileMapper.getAllCloudFiles(userService.getUserid(username));
    }

}
