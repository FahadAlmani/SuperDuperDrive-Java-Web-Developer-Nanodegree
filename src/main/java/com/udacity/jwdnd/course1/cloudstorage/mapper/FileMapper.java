package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface FileMapper {
    @Select("SELECT filename FROM FILES WHERE filename = #{fileName}")
    String getFileName(String fileName);
    @Select("SELECT * FROM FILES")
    ArrayList<FileModel> getFiles();

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileDate})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(FileModel fileModel);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    void delete(int id);
}
