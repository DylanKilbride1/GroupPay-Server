package com.dylankilbride.grouppay.models;


import javax.persistence.*;

@Entity
public class ProfileImage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "profile_image_id")
	private long imageId;

	@Column(name = "profile_image_location")
	private String profileImageLocation;

	public ProfileImage(String profileImageLocation) {
		this.profileImageLocation = profileImageLocation;
	}

	public ProfileImage() {}

	public String getProfileImageLocation() {
		return profileImageLocation;
	}

	public void setProfileImageLocation(String profileImageLocation) {
		this.profileImageLocation = profileImageLocation;
	}

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}
}