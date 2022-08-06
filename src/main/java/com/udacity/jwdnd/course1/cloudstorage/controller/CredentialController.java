package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;
import java.text.MessageFormat;

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

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final String credentialSuccessMessage = "credentialSuccessMessage";
    private final String credentialErrorMessage = "credentialErrorMessage";
    private final String credentialExceptionMessage = "There was an exception trying to store the credential.";

    private StorageService storageService;
    private UserService userService;

    private static final Logger LOG = LogManager.getLogger(CredentialController.class);

    public CredentialController(StorageService storageService, UserService userService) {
        this.storageService = storageService;
        this.userService = userService;
    }

    @PostMapping
    public String handleCredentialSubmit(Credential credential, Model model, RedirectAttributes redirectAttributes,
            Authentication authentication) throws IOException {
        String username = authentication.getName();

        LOG.info(MessageFormat.format("User {0} submitting credential with username ''{0}'' ", username,
                credential.getUsername()));

        switch(storageService.credentialSubmit(username, credential)) {
            case CREDENTIAL_INSERT_SUCCESS:
                redirectAttributes.addFlashAttribute(credentialSuccessMessage, "Credential Submit Success!");
                break;
            case CREDENTIAL_INSERT_FAILURE:
                redirectAttributes.addFlashAttribute(credentialErrorMessage,
                        "There was an error inserting the credential.");
                break;
            case CREDENTIAL_INSERT_EXCEPTION:
                redirectAttributes.addFlashAttribute(credentialErrorMessage, credentialExceptionMessage);
                break;
            case CREDENTIAL_SAVE_SUCCESS:
                redirectAttributes.addFlashAttribute(credentialSuccessMessage, "Credential Save Success!");
                break;
            case CREDENTIAL_SAVE_FAILURE:
                redirectAttributes.addFlashAttribute(credentialErrorMessage, credentialExceptionMessage);
                break;
            case CREDENTIAL_SAVE_EXCEPTION:
                redirectAttributes.addFlashAttribute(credentialErrorMessage,
                        "There was an exception trying to store the credential.");
                break;
            default:
                LOG.debug(MessageFormat.format("Unknown result submitting credential with username ''{0}'' ",
                        credential.getUsername()));
                break;
        }
        model.addAttribute("credentials", this.storageService.getAllCredentials(username));
        return "redirect:/home";
    }

    @GetMapping(value = "/credentialHandling/delete/{credentialid}")
    public String deleteCredential(@PathVariable Integer credentialid, Model model,
            RedirectAttributes redirectAttributes, Authentication authentication) throws IOException {

        switch(storageService.deleteCredential(credentialid)) {
            case CREDENTIAL_DELETE_SUCCESS:
                redirectAttributes.addFlashAttribute(credentialSuccessMessage, "Credential Delete Success!");
                break;
            case CREDENTIAL_DELETE_EXCEPTION:
                redirectAttributes.addFlashAttribute(credentialErrorMessage,
                        MessageFormat.format("Error deleting credential with id ''{0}'' ", credentialid));
                break;
            default:
                LOG.debug(MessageFormat.format("Unknown result deleting credential with id ''{0}'' ", credentialid));
                break;
        }

        model.addAttribute("credentials", this.storageService.getAllCredentials(authentication.getName()));
        return "redirect:/home";
    }
}
