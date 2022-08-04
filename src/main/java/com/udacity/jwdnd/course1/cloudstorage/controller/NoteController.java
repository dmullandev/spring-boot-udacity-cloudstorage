package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;
import java.text.MessageFormat;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String handleNoteSubmit(Note note, Model model, Authentication authentication) throws IOException {
        String username = authentication.getName();

        LOG.info(MessageFormat.format("Submitting note with title ''{0}'' for username ''{1}'' ", note.getNotetitle(),
                username));

        final String success = "noteSuccessMessage";
        final String error = "noteErrorMessage";

        switch (storageService.noteSubmit(username, note)) {
        case NOTE_INSERT_SUCCESS:
            model.addAttribute(success, "Note Upload Success!");
            break;
        case NOTE_INSERT_FAILURE:
            model.addAttribute(error, "There was an error inserting the note.");
            break;
        case NOTE_SAVE_SUCCESS:
            model.addAttribute(success, "Note Save Success!");
            break;
        case NOTE_SAVE_FAILURE:
            model.addAttribute(error, "There was an error saving the note.");
            break;
        default:
            break;
        }

        model.addAttribute("notes", this.storageService.getNotes(username));
        return "result";
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("Creating NoteController bean");
    }
}
