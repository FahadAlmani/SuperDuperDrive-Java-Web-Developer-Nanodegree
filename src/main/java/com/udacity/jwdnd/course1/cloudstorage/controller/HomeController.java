package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/home")
public class HomeController {
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialsService credentialsService;

    public HomeController(FileService fileService, NoteService noteService, CredentialsService credentialsService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialsService = credentialsService;
    }

    @GetMapping
    public String getHome(Model model){
        model.addAttribute("files", this.fileService.getFiles());
        model.addAttribute("notes", this.noteService.getNotes());
        model.addAttribute("credentials", this.credentialsService.getCredential());
        return "home";
    }
}
