package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(path = "file")
    public String postFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Model model){
        String fileName = fileUpload.getOriginalFilename();
        ArrayList<FileModel> fileModels = this.fileService.saveFile(fileUpload);
        model.addAttribute("files", fileModels);
        if (fileUpload.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload");

            return "home";
        }

        if(this.fileService.isAvailableFileName(fileName)){
            try {
                FileModel FileModel =  new FileModel(fileUpload.getOriginalFilename(), fileUpload.getContentType(), String.valueOf(fileUpload.getSize()),1, fileUpload.getBytes());
                    fileModels.add(FileModel);
//                ArrayList<FileModel> fileModels = this.fileService.saveFile(fileUpload);
                model.addAttribute("message", "File uploaded");
//                model.addAttribute("files", fileModels);
                return "home";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        model.addAttribute("message", "The file name is already exist");
        return "home";
    }
}
