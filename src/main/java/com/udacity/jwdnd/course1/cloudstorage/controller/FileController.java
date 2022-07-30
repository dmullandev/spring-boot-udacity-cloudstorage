package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String handleFileUpload(@RequestParam(value = "fileUpload") MultipartFile file,
            RedirectAttributes redirectAttributes, Authentication authentication) throws IOException {
        // LOG.info("Uploading File for user: " + authentication.getName() + "for
        // filename: " + file.getMpf().getName());

        LOG.info("Uploading File for user: " + authentication.getName());
        // LOG.info("for filename: " + file.getMpf());

        String uploadError = null;

        Integer rowsAdded = storageService.insertFile(authentication.getName(), file);

        if (rowsAdded == -1) {
            uploadError = "There was an error uploading the file.";
        } else if (rowsAdded == -2) {
            uploadError = "There was a problem uploading the file: The file already exists for user "
                    + authentication.getName();
        }

        if (uploadError != null) {
            redirectAttributes.addAttribute("error", uploadError);
        } else {
            redirectAttributes.addAttribute("success", true);
        }

        redirectAttributes.addAttribute("cloudFiles", this.storageService.getCloudFiles(authentication.getName()));
        return "handleFileUpload";
    }

}
