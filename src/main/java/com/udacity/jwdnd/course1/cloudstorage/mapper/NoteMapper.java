package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Mapper
public interface NoteMapper {
	@Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
	Note getNote(String noteId);

	@Insert("INSERT INTO NOTES (noteid, notetitle, notedescription, userid) VALUES(#{noteId}, #{noteTitle}), #{noteDescription}), #{userId}), #{firstName}), #{lastName})")
	@Options(useGeneratedKeys = true, keyProperty = "noteid")
	Integer insertNote(Note note);

	@Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
	void deleteNote(Integer noteId);

	@Select("SELECT * FROM NOTES WHERE userid = #{userId}")
	List<Note> getNoteIdsByUser(Integer userId);
}