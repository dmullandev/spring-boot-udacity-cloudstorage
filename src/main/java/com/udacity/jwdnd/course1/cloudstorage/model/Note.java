package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
	private Integer noteId;
	private String noteTitle;
	private String noteDescription;
	private Integer userId;

	/**
	 * @param noteId
	 * @param noteTitle
	 * @param noteDescription
	 * @param userId
	 */
	public Note(Integer noteId, String noteTitle, String noteDescription, Integer userId) {
		this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteDescription = noteDescription;
		this.userId = userId;
	}

	/**
	 * @return the noteId
	 */
	public Integer getNoteId() {
		return noteId;
	}

	/**
	 * @param noteId the noteId to set
	 */
	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	/**
	 * @return the noteTitle
	 */
	public String getNoteTitle() {
		return noteTitle;
	}

	/**
	 * @param noteTitle the noteTitle to set
	 */
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	/**
	 * @return the noteDescription
	 */
	public String getNoteDescription() {
		return noteDescription;
	}

	/**
	 * @param noteDescription the noteDescription to set
	 */
	public void setNoteDescription(String noteDescription) {
		this.noteDescription = noteDescription;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
