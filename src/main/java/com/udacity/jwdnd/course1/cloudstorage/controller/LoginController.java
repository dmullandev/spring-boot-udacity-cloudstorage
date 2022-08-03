package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOG = LogManager.getLogger(LoginController.class);

    @GetMapping()
    public String loginView() {
        return "login";
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("Creating LoginController bean");
    }

}
