package com.dylankilbride.grouppay.ReturnObjects;

import com.dylankilbride.grouppay.Models.ProfileImage;

public class UsersProfileDetails {

	private long id;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String mobileNumber;
	private String profileUrl;

	public UsersProfileDetails(long id, String firstName, String lastName, String emailAddress, String mobileNumber, String profileUrl) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.mobileNumber = mobileNumber;
		this.profileUrl = profileUrl;
	}

	public UsersProfileDetails(String firstName, String lastName, String emailAddress, String mobileNumber, ProfileImage profileImage) {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
}