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
        System.out.println(noteModel.getNoteId());
        if(noteModel.getNoteId() == null) {
            // todo make user ID dynamic
            this.noteService.saveNote(new NoteModel(null,noteModel.getNoteTitle(), noteModel.getNoteDescription(), 1));
            attributes.addFlashAttribute("message", "Note Created");
        }else {
            // todo make user ID dynamic
            this.noteService.updateNote(new NoteModel(null,noteModel.getNoteTitle(), noteModel.getNoteDescription(), 1));
            attributes.addFlashAttribute("message", "Note Updated");
        }
        return "redirect:/home";
    }

    @GetMapping(path = "/deleteNote/{noteId}")
    public String deleteFile(@PathVariable(name = "noteId") int noteId, RedirectAttributes attributes){
        this.noteService.deleteNote(noteId);
        attributes.addFlashAttribute("files", this.noteService.getNotes());
        attributes.addFlashAttribute("message", "Note Deleted");
        return "redirect:/home";
    }
}
