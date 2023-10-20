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

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public boolean isAvailableFileName(String fileName){
        return this.fileMapper.getFileName(fileName) == null;
    }

    public int saveFile(MultipartFile fileUpload){
        try {
            // todo make user ID dynamic
            FileModel fileModel = new FileModel(fileUpload.getOriginalFilename(),
                    fileUpload.getContentType(),
                    String.valueOf(fileUpload.getSize()),
                    1,
                    fileUpload.getBytes());
            int numberOfRowsAdded = this.fileMapper.insert(fileModel);
            return numberOfRowsAdded;

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
