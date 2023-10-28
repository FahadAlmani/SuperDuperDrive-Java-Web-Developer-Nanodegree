package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int saveNote(NoteModel noteModel){
            // todo make user ID dynamic
        return this.noteMapper.insert( new NoteModel(null, noteModel.getNoteTitle(),
                    noteModel.getNoteDescription(),
                    1));
    }
    public ArrayList<NoteModel> getNotes(){
        return this.noteMapper.getNotes();
    }
    public void deleteNote(int noteId){
        this.noteMapper.delete(noteId);
    }
    public void updateNote(NoteModel noteModel) {
         this.noteMapper.updateNote(noteModel);
    }
}
