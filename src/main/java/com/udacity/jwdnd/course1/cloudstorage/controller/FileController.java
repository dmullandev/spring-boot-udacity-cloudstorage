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

        String fileUploadError = null;

        Integer rowsAdded = storageService.insertFile(username, fileUpload);

        if (rowsAdded == -1) {
            fileUploadError = "There was an error uploading the file.";
        } else if (rowsAdded == -2) {
            fileUploadError = MessageFormat.format(
                    "There was a problem uploading the file: The file ''{0}'' already exists",
                    fileUpload.getOriginalFilename());
        }

        if (fileUploadError != null) {
            model.addAttribute("fileErrorMessage", fileUploadError);
        } else {
            model.addAttribute("fileSuccessMessage", "File Upload Success!");
        }

        model.addAttribute("cloudFiles", this.storageService.getCloudFiles(username));
        return "home";
    }

    @GetMapping(value = "/fileHandling/delete/{filename}")
    public String deleteCloudFile(@PathVariable String filename, Model model, Authentication authentication)
            throws IOException {

        if (storageService.deleteFile(filename) != 200) {
            model.addAttribute("fileErrorMessage", MessageFormat.format("Error deleting filename {0}", filename));
        } else {
            model.addAttribute("fileSuccessMessage", "File Delete Success!");
        }

        model.addAttribute("cloudFiles", this.storageService.getCloudFiles(authentication.getName()));
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
