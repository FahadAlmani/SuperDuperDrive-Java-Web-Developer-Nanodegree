package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialsModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class CredentialsController {

    private final CredentialsService credentialsService;

    public CredentialsController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @PostMapping(path = "/credential")
    public String saveCredential(@ModelAttribute CredentialsModel credentialsModel, RedirectAttributes attributes){
        if(credentialsModel.getCredentialId() == null) {
            this.credentialsService.createCredential(credentialsModel);
            attributes.addFlashAttribute("message", "Credential Created");
        }else {
            this.credentialsService.updateCredential(credentialsModel);
            attributes.addFlashAttribute("message", "Credential Updated");
        }
        return "redirect:/home";
    }

    @GetMapping(path = "/deleteCredential/{credentialId}")
    public String deleteCredential(@PathVariable(name = "credentialId") int credentialId, RedirectAttributes attributes){
        this.credentialsService.deleteCredential(credentialId);
        attributes.addFlashAttribute("message", "Credential Deleted");
        return "redirect:/home";
    }
}
