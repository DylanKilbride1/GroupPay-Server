package com.dylankilbride.grouppay.models;

import javax.persistence.*;

@Entity
public class ProfileImage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "profile_image_id")
	private long profileImageId;

	@Column(name = "profile_image_location")
	private String profileImageLocation;

	@OneToOne(fetch = FetchType.EAGER,
					cascade = CascadeType.ALL,
					mappedBy = "profileImage")
	private User user;
}