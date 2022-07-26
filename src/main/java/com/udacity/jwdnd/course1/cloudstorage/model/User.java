package com.udacity.jwdnd.course1.cloudstorage.model;

public class User {
	private Integer userid;
	private String username;
	private String salt;
	private String password;
	private String firstname;
	private String lastname;

	/**
	 * @param userId
	 * @param username
	 * @param salt
	 * @param password
	 * @param firstName
	 * @param lastName
	 */
	public User(Integer userId, String username, String salt, String password, String firstname, String lastname) {
		this.userid = userid;
		this.username = username;
		this.salt = salt;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the userName to set
	 */
	public void setusername(String username) {
		this.username = username;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
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
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstName the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
