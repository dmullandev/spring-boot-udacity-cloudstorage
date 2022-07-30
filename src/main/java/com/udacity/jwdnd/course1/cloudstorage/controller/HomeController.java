package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;

@Controller
@RequestMapping("/home")
public class HomeController {

    private StorageService storageService;

    public HomeController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public String homeView(Authentication authentication, Model model) {
        model.addAttribute("cloudFiles", storageService.getCloudFiles(authentication.getName()));
        return "home";
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating HomeController bean");
    }

}
