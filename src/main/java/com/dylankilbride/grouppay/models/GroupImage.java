package com.dylankilbride.grouppay.models;

import javax.persistence.*;

@Entity
public class GroupImage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "group_image_id")
	private long id;


	@Column(name = "group_image_location")
	private String groupImageLocation;

	public GroupImage(String groupImageLocation) {
		this.groupImageLocation = groupImageLocation;
	}

	public GroupImage() {
	}

	public String getGroupImageLocation() {
		return groupImageLocation;
	}

	public void setGroupImageLocation(String groupImageLocation) {
		this.groupImageLocation = groupImageLocation;
	}
}