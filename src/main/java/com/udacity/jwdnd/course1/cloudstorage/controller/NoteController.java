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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final String noteSuccessMessage = "noteSuccessMessage";
    private final String noteErrorMessage = "noteErrorMessage";

    private StorageService storageService;
    private UserService userService;

    private static final Logger LOG = LogManager.getLogger(NoteController.class);

    public NoteController(StorageService storageService, UserService userService) {
        this.storageService = storageService;
        this.userService = userService;
    }

    @PostMapping
    public String handleNoteSubmit(Note note, Model model, RedirectAttributes redirectAttributes,
            Authentication authentication) throws IOException {
        String username = authentication.getName();

        LOG.info(MessageFormat.format("Submitting note with title ''{0}'' for username ''{1}'' ", note.getNotetitle(),
                username));

        switch(storageService.noteSubmit(username, note)) {
            case NOTE_INSERT_SUCCESS:
                redirectAttributes.addFlashAttribute(noteSuccessMessage, "Note Submit Success!");
                break;
            case NOTE_INSERT_FAILURE:
                redirectAttributes.addFlashAttribute(noteErrorMessage, "There was an error inserting the note.");
                break;
            case NOTE_SAVE_SUCCESS:
                redirectAttributes.addFlashAttribute(noteSuccessMessage, "Note Edit Success!");
                break;
            case NOTE_SAVE_FAILURE:
                redirectAttributes.addFlashAttribute(noteErrorMessage, "There was an error saving the note.");
                break;
            default:
                LOG.debug(
                        MessageFormat.format("Unknown result submitting note with noteid ''{0}'' ", note.getNoteid()));
                break;
        }

        model.addAttribute("notes", this.storageService.getAllNotes(username));
        return "redirect:/home";
    }

    @GetMapping(value = "/noteHandling/delete/{noteid}")
    public String deleteNote(@PathVariable Integer noteid, Model model, RedirectAttributes redirectAttributes,
            Authentication authentication) throws IOException {

        switch(storageService.deleteNote(noteid)) {
            case NOTE_DELETE_SUCCESS:
                redirectAttributes.addFlashAttribute(noteSuccessMessage, "Note Delete Success!");
                break;
            case NOTE_DELETE_EXCEPTION:
                redirectAttributes.addFlashAttribute(noteErrorMessage,
                        MessageFormat.format("Error deleting note with id ''{0}'' ", noteid));
                break;
            default:
                LOG.debug(MessageFormat.format("Unknown result deleting note with id ''{0}'' ", noteid));
                break;
        }

        model.addAttribute("notes", this.storageService.getAllNotes(authentication.getName()));
        return "redirect:/home";
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("Creating NoteController bean");
    }
}
