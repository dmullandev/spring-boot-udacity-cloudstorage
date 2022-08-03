package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;
import java.text.MessageFormat;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/note")
public class NoteController {

    private StorageService storageService;
    private UserService userService;

    private static final Logger LOG = LogManager.getLogger(NoteController.class);

    public NoteController(StorageService storageService, UserService userService) {
        this.storageService = storageService;
        this.userService = userService;
    }

    @PostMapping
    public String handleNoteUpload(@ModelAttribute("noteTitle") String noteTitle,
            @ModelAttribute("noteDescription") String noteDescription, Model model, Authentication authentication)
            throws IOException {
        String username = authentication.getName();

        LOG.info(MessageFormat.format("Uploading Note with title ''{0}'' for username ''{1}'' ", noteTitle, username));

        String noteUploadError = null;

        Integer rowsAdded = storageService
                .insertNote(new Note(null, noteTitle, noteDescription, userService.getUserid(username)));

        if (rowsAdded == -1) {
            noteUploadError = "There was an error uploading the note.";
        } else if (rowsAdded == -2) {
            noteUploadError = MessageFormat.format(
                    "There was a problem uploading the note: The note with title ''{0}'' already exists", noteTitle);
        }

        if (noteUploadError != null) {
            model.addAttribute("noteErrorMessage", noteUploadError);
        } else {
            model.addAttribute("noteSuccessMessage", "Note Upload Success!");
        }

        model.addAttribute("notes", this.storageService.getNotes(username));
        return "home";
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("Creating NoteController bean");
    }
}
