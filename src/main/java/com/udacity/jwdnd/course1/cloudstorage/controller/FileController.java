package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;
import java.text.MessageFormat;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;

@Controller
@RequestMapping("/file")
public class FileController {

    private StorageService storageService;

    private final String fileSuccessMessage = "fileSuccessMessage";
    private final String fileeErrorMessage = "fileErrorMessage";

    private static final Logger LOG = LogManager.getLogger(FileController.class);

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping
    public String handleFileUpload(@ModelAttribute("fileUpload") MultipartFile fileUpload, Model model,
            Authentication authentication) throws IOException {
        String username = authentication.getName();

        LOG.info(MessageFormat.format("Uploading CloudFile with filename ''{0}'' for username ''{1}'' ",
                fileUpload.getOriginalFilename(), username));

        final String success = "fileSuccessMessage";
        final String error = "fileErrorMessage";

        switch(storageService.insertFile(username, fileUpload)) {
            case FILE_INSERT_SUCCESS:
                model.addAttribute(fileSuccessMessage, "File Upload Success!");
                break;
            case FILE_INSERT_FAILURE:
                model.addAttribute(fileeErrorMessage, "There was an error inserting the file.");
                break;
            case FILE_INSERT_DUPLICATE:
                model.addAttribute(fileeErrorMessage,
                        MessageFormat.format("There was a problem uploading the file: The file ''{0}'' already exists",
                                fileUpload.getOriginalFilename()));
                break;
            default:
                LOG.debug(MessageFormat.format("Unknown result uploading file with filename {0} ",
                        fileUpload.getOriginalFilename()));
                break;
        }

        model.addAttribute("cloudFiles", this.storageService.getAllCloudFiles(username));
        return "home";
    }

    @GetMapping(value = "/fileHandling/delete/{filename}")
    public String deleteCloudFile(@PathVariable String filename, Model model, Authentication authentication)
            throws IOException {

        switch(storageService.deleteFile(filename)) {
            case FILE_DELETE_SUCCESS:
                model.addAttribute(fileSuccessMessage, "File Delete Success!");
                break;
            case FILE_DELETE_EXCEPTION:
                model.addAttribute(fileeErrorMessage,
                        MessageFormat.format("Error deleting filename ''{0}'' ", filename));
                break;
            default:
                LOG.debug(MessageFormat.format("Unknown result deleting file with filename {0} ", filename));
                break;
        }

        model.addAttribute("cloudFiles", this.storageService.getAllCloudFiles(authentication.getName()));
        return "home";
    }

    @GetMapping(value = "/fileHandling/view/{filename}", produces = org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] viewCLoudFile(@PathVariable String filename) throws IOException {
        return storageService.viewFile(filename);
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("Creating FileController bean");
    }
}
