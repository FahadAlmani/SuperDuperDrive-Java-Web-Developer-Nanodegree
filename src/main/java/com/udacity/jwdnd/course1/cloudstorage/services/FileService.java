package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public boolean isAvailableFileName(String fileName){
        return this.fileMapper.getFileName(fileName) == null;
    }

    public int saveFile(MultipartFile fileUpload){
        try {
            return this.fileMapper.insert(new FileModel(null,fileUpload.getOriginalFilename(),
                    fileUpload.getContentType(),
                    String.valueOf(fileUpload.getSize()),
                    this.userService.getUserId(),
                    fileUpload.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload the file.", e);
        }
    }
    public ArrayList<FileModel> getFiles(){
        return this.fileMapper.getFiles();
    }
    public void deleteFile(int fileId){
        this.fileMapper.delete(fileId);
    }
    public FileModel getFileById(int fileId){
        return this.fileMapper.getFile(fileId);
    }
}
