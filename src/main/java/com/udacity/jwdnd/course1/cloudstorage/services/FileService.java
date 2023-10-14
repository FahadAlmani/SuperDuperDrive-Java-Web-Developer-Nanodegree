package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
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

    public ArrayList<FileModel> saveFile(MultipartFile fileUpload){
        try {
            String currentDirectory = System.getProperty("user.dir");
            String uploadDirectory = "/uploadFiles/";
            String originalFilename = fileUpload.getOriginalFilename();

            String filePath = currentDirectory + uploadDirectory + originalFilename;

            // todo make user ID dynamic
            FileModel fileModel = new FileModel(originalFilename, fileUpload.getContentType(), String.valueOf(fileUpload.getSize()),1, fileUpload.getBytes());
            this.fileMapper.insert(fileModel);

            fileUpload.transferTo(new File(filePath));

            return this.fileMapper.getFiles();

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload the file.", e);
        }
    }
}
