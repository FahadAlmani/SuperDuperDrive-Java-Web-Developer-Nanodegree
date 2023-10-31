package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(path = "/uploadFile")
    public String postFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, RedirectAttributes attributes){
        if (fileUpload.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload");
        }else if(this.fileService.isAvailableFileName(fileUpload.getOriginalFilename())){
                this.fileService.saveFile(fileUpload);
                attributes.addFlashAttribute("message", "File uploaded");
        }else {
            attributes.addFlashAttribute("message", "The file name is already exist");
        }
        return "redirect:/home";
    }

    @GetMapping(path = "/deleteFile/{fileId}")
    public String deleteFile(@PathVariable(name = "fileId") int fileId, RedirectAttributes attributes){
        this.fileService.deleteFile(fileId);
        attributes.addFlashAttribute("message", "File Deleted");
        return "redirect:/home";
    }

    @GetMapping(path = "/downloadFile/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable(name = "fileId") int fileId){
        FileModel FileModel = fileService.getFileById(fileId);
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=" + FileModel.getFileName())
                .contentType(MediaType.parseMediaType(FileModel.getContentType()))
                .body(FileModel.getFileData());
    }
}
