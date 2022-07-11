package com.udacity.jwdnd.course1.cloudstorage.model;

public class CloudFile {
	private Integer fileId;
	private String fileName;
	private String contentType;
	private String fileSize;
	private Integer userId;
	private byte[] fileData;

	/**
	 * @param fileId
	 * @param fileName
	 * @param contentType
	 * @param fileSize
	 * @param userId
	 * @param fileData
	 */
	public CloudFile(Integer fileId, String fileName, String contentType, String fileSize, Integer userId,
			byte[] fileData) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileSize = fileSize;
		this.userId = userId;
		this.fileData = fileData;
	}

	/**
	 * @return the fileId
	 */
	public Integer getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the fileSize
	 */
	public String getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
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

	/**
	 * @return the fileData
	 */
	public byte[] getFileData() {
		return fileData;
	}

	/**
	 * @param fileData the fileData to set
	 */
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

}
