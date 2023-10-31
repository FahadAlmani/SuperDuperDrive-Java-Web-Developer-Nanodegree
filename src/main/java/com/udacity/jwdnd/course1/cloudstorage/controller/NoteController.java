package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping(path = "/note")
    public String saveNote(@ModelAttribute NoteModel noteModel, RedirectAttributes attributes){
        if(noteModel.getNoteId() == null) {
            this.noteService.saveNote(noteModel);
            attributes.addFlashAttribute("message", "Note Created");
        }else {
            this.noteService.updateNote(noteModel);
            attributes.addFlashAttribute("message", "Note Updated");
        }
        return "redirect:/home";
    }

    @GetMapping(path = "/deleteNote/{noteId}")
    public String deleteFile(@PathVariable(name = "noteId") int noteId, RedirectAttributes attributes){
        this.noteService.deleteNote(noteId);
        attributes.addFlashAttribute("message", "Note Deleted");
        return "redirect:/home";
    }
}
