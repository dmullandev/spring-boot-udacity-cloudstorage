package com.udacity.jwdnd.course1.cloudstorage.services;

import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.CREDENTIAL_DELETE_EXCEPTION;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.CREDENTIAL_DELETE_SUCCESS;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.CREDENTIAL_INSERT_EXCEPTION;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.CREDENTIAL_INSERT_FAILURE;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.CREDENTIAL_INSERT_SUCCESS;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.CREDENTIAL_SAVE_EXCEPTION;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.CREDENTIAL_SAVE_FAILURE;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.CREDENTIAL_SAVE_SUCCESS;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.FILE_DELETE_EXCEPTION;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.FILE_DELETE_SUCCESS;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.FILE_INSERT_DUPLICATE;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.FILE_INSERT_EXCEPTION;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.FILE_INSERT_FAILURE;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.FILE_INSERT_SUCCESS;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.NOTE_DELETE_EXCEPTION;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.NOTE_DELETE_SUCCESS;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.NOTE_INSERT_EXCEPTION;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.NOTE_INSERT_FAILURE;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.NOTE_INSERT_SUCCESS;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.NOTE_SAVE_EXCEPTION;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.NOTE_SAVE_FAILURE;
import static com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum.NOTE_SAVE_SUCCESS;
import static com.udacity.jwdnd.course1.cloudstorage.utilities.EncodingUtils.createEncodedKey;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.controller.enums.ResultEnum;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CloudFileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CloudFile;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Service
public class StorageService {

    private static final Logger LOG = LogManager.getLogger(StorageService.class);

    private CloudFileMapper cloudFileMapper;
    private UserService userService;
    private NoteMapper noteMapper;
    private CredentialMapper credentialMapper;
    private HashService hashService;

    public StorageService(CloudFileMapper cloudFileMapper, NoteMapper noteMapper, CredentialMapper credentialMapper,
            UserService userService, HashService hashService) {
        this.cloudFileMapper = cloudFileMapper;
        this.noteMapper = noteMapper;
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.hashService = hashService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating FileHandlingService bean");
    }

    public ResultEnum insertFile(String username, MultipartFile mpf) {

        LOG.debug(MessageFormat.format("Filename: {0}, Contenttype: {1}, Filesize: {2}, Userid: {3}",
                mpf.getOriginalFilename(), mpf.getContentType(), mpf.getSize(), username));

        try {
            if (cloudFileMapper.getCloudFile(userService.getUserid(username), mpf.getOriginalFilename()) == null) {
                LOG.info("Inserting CloudFile for username: " + username + " for filename: "
                        + mpf.getOriginalFilename());
                Integer rowsAdded = cloudFileMapper
                        .insertCloudFile(new CloudFile(null, mpf.getOriginalFilename(), mpf.getContentType(),
                                String.valueOf(mpf.getSize()), userService.getUserid(username), mpf.getBytes()));
                return (rowsAdded > 0) ? FILE_INSERT_SUCCESS : FILE_INSERT_FAILURE;
            } else {
                return FILE_INSERT_DUPLICATE;
            }

        } catch (IOException ioe) {
            LOG.error(ioe.getMessage());
            return FILE_INSERT_EXCEPTION;
        }
    }

    public ResultEnum noteSubmit(String username, Note note) {

        boolean saving = note.getNoteid() != 0;

        try {
            if (!saving) {
                LOG.info(MessageFormat.format(
                        "Inserting Note Title: ''{0}'', Description: ''{1}'', UserId: ''{2}'', NoteId: ''{3}''",
                        note.getNotetitle(), note.getNotedescription(), note.getUserid(), note.getNoteid()));
                note.setUserid(userService.getUserid(username));

                Integer rowsAdded = noteMapper.insertNote(note);

                return (rowsAdded > 0) ? NOTE_INSERT_SUCCESS : NOTE_INSERT_FAILURE;
            } else {
                LOG.info(MessageFormat.format(
                        "Saving Note Title: ''{0}'', Description: ''{1}'', UserId: ''{2}'', NoteId: ''{3}''",
                        note.getNotetitle(), note.getNotedescription(), note.getUserid(), note.getNoteid()));
                Integer rowsSaved = noteMapper.updateNote(note);

                return (rowsSaved > 0) ? NOTE_SAVE_SUCCESS : NOTE_SAVE_FAILURE;
            }

        } catch (Exception e) {
            LOG.error(e.getMessage());
            return !saving ? NOTE_INSERT_EXCEPTION : NOTE_SAVE_EXCEPTION;
        }
    }

    public ResultEnum credentialSubmit(String username, Credential credential) {

        boolean saving = credential.getCredentialid() != null && credential.getCredentialid() != 0;
        EncryptionService encryptionService = new EncryptionService();

        try {
            if (!saving) {
                LOG.info(MessageFormat.format("User ''{0}'' inserting Credential with username: ''{1}'' ", username,
                        credential.getUsername()));

                credential.setUserid(userService.getUserid(username));
                credential.setKey(createEncodedKey());
                credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));

                Integer rowsAdded = credentialMapper.insertCredential(credential);

                return (rowsAdded > 0) ? CREDENTIAL_INSERT_SUCCESS : CREDENTIAL_INSERT_FAILURE;
            } else {
                LOG.info(MessageFormat.format("User ''{0}'' saving Credential with username: ''{1}'', id: ''{2}'' ",
                        username, credential.getUsername(), credential.getUserid()));

                Credential tempCredential = credentialMapper
                        .getCredential(String.valueOf(credential.getCredentialid()));

                credential.setUserid(tempCredential.getUserid());
                credential.setKey(tempCredential.getKey());
                credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));

                Integer rowsSaved = credentialMapper.updateCredential(credential);

                return (rowsSaved > 0) ? CREDENTIAL_SAVE_SUCCESS : CREDENTIAL_SAVE_FAILURE;
            }

        } catch (Exception e) {
            LOG.error(e.getMessage());
            return !saving ? CREDENTIAL_INSERT_EXCEPTION : CREDENTIAL_SAVE_EXCEPTION;
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

    public ResultEnum deleteFile(String filename) {
        try {
            LOG.info(MessageFormat.format("Deleting Cloudfile data with filename {0}", filename));
            cloudFileMapper.deleteCloudFile(cloudFileMapper.getCloudFileId(filename));
            return FILE_DELETE_SUCCESS;
        } catch (Exception e) {
            return FILE_DELETE_EXCEPTION;
        }

    }

    public ResultEnum deleteNote(Integer noteid) {
        try {
            LOG.info(MessageFormat.format("Deleting note with noteid {0}", noteid));
            noteMapper.deleteNote(noteid);
            return NOTE_DELETE_SUCCESS;
        } catch (Exception e) {
            return NOTE_DELETE_EXCEPTION;
        }

    }

    public ResultEnum deleteCredential(Integer credentialid) {
        try {
            LOG.info(MessageFormat.format("Deleting credential with credentialid {0}", credentialid));
            credentialMapper.deleteCredential(credentialid);
            return CREDENTIAL_DELETE_SUCCESS;
        } catch (Exception e) {
            return CREDENTIAL_DELETE_EXCEPTION;
        }

    }

    /**
     * 
     * @param username the username to get all files for
     * @return the list of all uploaded {@link CloudFile}s for a {@link User}
     */
    public String[] getAllCloudFiles(String username) {
        return cloudFileMapper.getAllCloudFiles(userService.getUserid(username)).stream()
                .map(file -> file.getFilename()).collect(Collectors.toList()).toArray(String[]::new);
    }

    /**
     * 
     * @param username the username to get all notes for
     * @return the list of all created {@link Note}s for a {@link User}
     */
    public List<Note> getAllNotes(String username) {
        return noteMapper.getAllNotes(userService.getUserid(username)).stream().collect(Collectors.toList());
    }

    /**
     * 
     * @param username the username to get all credentials for
     * @return the list of all created {@link Credential}s for a {@link User}
     */
    public List<Credential> getAllCredentials(String username) {
        return credentialMapper.getAllCredentials(userService.getUserid(username)).stream()
                .collect(Collectors.toList());
    }

}
