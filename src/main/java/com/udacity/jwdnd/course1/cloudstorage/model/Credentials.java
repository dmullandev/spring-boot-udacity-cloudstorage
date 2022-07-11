package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credentials {
	private Integer credentialId;
	private String url;
	private String username;
	private String key;
	private String password;
	private Integer userId;

	/**
	 * @param credentialId
	 * @param url
	 * @param username
	 * @param key
	 * @param password
	 * @param userId
	 */
	public Credentials(Integer credentialId, String url, String username, String key, String password, Integer userId) {
		this.credentialId = credentialId;
		this.url = url;
		this.username = username;
		this.key = key;
		this.password = password;
		this.userId = userId;
	}

	/**
	 * @return the credentialId
	 */
	public Integer getCredentialId() {
		return credentialId;
	}

	/**
	 * @param credentialId the credentialId to set
	 */
	public void setCredentialId(Integer credentialId) {
		this.credentialId = credentialId;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
