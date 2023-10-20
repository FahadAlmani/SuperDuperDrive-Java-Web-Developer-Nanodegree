package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    FileModel getFileName(String fileName);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    FileModel getFile(int fileId);

    @Select("SELECT * FROM FILES")
    ArrayList<FileModel> getFiles();

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(FileModel fileModel);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void delete(int id);
}
