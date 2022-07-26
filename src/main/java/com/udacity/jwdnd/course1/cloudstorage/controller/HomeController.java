package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudFile;
import com.udacity.jwdnd.course1.cloudstorage.services.CloudFileService;

@Controller
@RequestMapping("/home")
public class HomeController {

	private CloudFileService cloudFileService;

	public HomeController(CloudFileService cloudFileService) {
		this.cloudFileService = cloudFileService;
	}

	@GetMapping()
	public String homeView(Authentication authentication, Model model) {
		model.addAttribute("cloudFiles", this.cloudFileService.getCloudFiles());
		return "home";
	}

	@PostMapping()
	public String uploadCloudFile(Authentication authentication, @ModelAttribute CloudFile cloudFile, Model model) {
		this.cloudFileService.addCloudFile(authentication.getName(), cloudFile);
		model.addAttribute("cloudFiles", this.cloudFileService.getCloudFiles());
		return "cloudFileUpload";
	}

}
