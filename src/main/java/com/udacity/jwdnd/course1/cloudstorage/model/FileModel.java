package com.udacity.jwdnd.course1.cloudstorage.model;

import java.sql.Date;

public class FileModel {
    private int fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private int userId;
    private byte[] fileDate;

    public FileModel(String fileName, String contentType, String fileSize, int userId, byte[] fileDate) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileDate = fileDate;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] getFileDate() {
        return fileDate;
    }

    public void setFileDate(byte[] fileDate) {
        this.fileDate = fileDate;
    }
}
