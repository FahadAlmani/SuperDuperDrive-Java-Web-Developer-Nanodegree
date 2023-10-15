package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequestMapping(path = "/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(path = "/upload")
    public String postFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Model model){
        if (fileUpload.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");
            model.addAttribute("files", this.fileService.getFiles());
            return "redirect:/home";
        }

        if(this.fileService.isAvailableFileName(fileUpload.getOriginalFilename())){
            try {
                FileModel FileModel =  new FileModel(fileUpload.getOriginalFilename(),
                        fileUpload.getContentType(),
                        String.valueOf(fileUpload.getSize()),
                        1,
                        fileUpload.getBytes());
                this.fileService.saveFile(fileUpload);
                model.addAttribute("message", "File uploaded");
                model.addAttribute("files", this.fileService.getFiles());
                return "redirect:/home";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        model.addAttribute("message", "The file name is already exist");
        model.addAttribute("files", this.fileService.getFiles());
        return "home";
    }

    @GetMapping(path = "/delete/{fileId}")
    public String deleteFile(@PathVariable(name = "fileId") int fileId, Model model){
        this.fileService.deleteFile(fileId);
        model.addAttribute("files", this.fileService.getFiles());
        return "redirect:/home";
    }
}
