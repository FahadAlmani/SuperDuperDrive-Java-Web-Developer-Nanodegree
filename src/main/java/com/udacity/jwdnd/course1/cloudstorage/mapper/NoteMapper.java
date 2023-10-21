package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES")
    ArrayList<NoteModel> getNotes();

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(NoteModel noteModel);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void delete(int id);

    @Update("Update NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription}")
    void updateNote(NoteModel noteModel);

}
