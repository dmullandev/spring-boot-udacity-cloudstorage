package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CloudFileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CloudFile;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Service
public class StorageService {

    private static final Logger LOG = LogManager.getLogger(StorageService.class);

    private CloudFileMapper cloudFileMapper;
    private UserService userService;
    private NoteMapper noteMapper;
    private CredentialsMapper credentialsMapper;

    public StorageService(CloudFileMapper cloudFileMapper, NoteMapper noteMapper, CredentialsMapper credentialsMapper,
            UserService userService) {
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

        LOG.debug(MessageFormat.format("Filename: {0}, Contenttype: {1}, Filesize: {2}, Userid: {3}",
                mpf.getOriginalFilename(), mpf.getContentType(), mpf.getSize(), username));

        try {
            if (cloudFileMapper.getCloudFile(userService.getUserid(username), mpf.getOriginalFilename()) == null) {
                LOG.info("Inserting CloudFile for username: " + username + " for filename: "
                        + mpf.getOriginalFilename());

                return cloudFileMapper
                        .insertCloudFile(new CloudFile(null, mpf.getOriginalFilename(), mpf.getContentType(),
                                String.valueOf(mpf.getSize()), userService.getUserid(username), mpf.getBytes()));
            } else {
                // -2 file already exists
                return -2;
            }

        } catch (IOException ioe) {
            LOG.error(ioe.getMessage());
            return -1;
        }
    }

    public Integer insertNote(Note note) {

        LOG.debug(MessageFormat.format("Note Title: {0}, Description: {1}, UserId: {2}, NoteId: {3}",
                note.getNotetitle(), note.getNotedescription(), note.getUserid(), note.getNoteid()));

        try {
            if (noteMapper.getNote(note.getUserid(), note.getNotetitle()) == null) {
                LOG.info(
                        "Inserting Note for username: " + note.getUserid() + " for Note Title: " + note.getNotetitle());

                return noteMapper.insertNote(note);
            } else {
                // -2 file already exists
                return -2;
            }

        } catch (Exception e) {
            LOG.error(e.getMessage());
            return -1;
        }
    }

    public byte[] viewFile(String username, String filename) {
        LOG.info(MessageFormat.format("Viewing Cloudfile data for filename {0}", filename));

        return cloudFileMapper.getCloudFile(userService.getUserid(username), filename).getFiledata();
    }

    public byte[] viewFile(String filename) {
        LOG.info(MessageFormat.format("Viewing Cloudfile data for filename {0}", filename));

        return cloudFileMapper.viewCloudFile(filename).getFiledata();
    }

    public Integer deleteFile(String filename) {
        try {
            LOG.info(MessageFormat.format("Deleting Cloudfile data with filename {0}", filename));
            cloudFileMapper.deleteCloudFile(cloudFileMapper.getCloudFileId(filename));
            return 200;
        } catch (Exception e) {
            return -1;
        }

    }

    /**
     * 
     * @param username the username to get all files for
     * @return the list of all uploaded {@link CloudFile}s for {@link User}
     */
    public String[] getCloudFiles(String username) {
        return cloudFileMapper.getAllCloudFiles(userService.getUserid(username)).stream()
                .map(file -> file.getFilename()).collect(Collectors.toList()).toArray(String[]::new);
    }

    /**
     * 
     * @param username the username to get all files for
     * @return the list of all uploaded {@link CloudFile}s for {@link User}
     */
    public List<Note> getNotes(String username) {
        return noteMapper.getNotesByUserid(userService.getUserid(username)).stream().collect(Collectors.toList());
    }
}
