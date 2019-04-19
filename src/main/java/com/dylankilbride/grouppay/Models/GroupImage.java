package com.dylankilbride.grouppay.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class GroupImage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "group_image_id")
	@JsonIgnore
	private long groupImageId;

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

	public long getGroupImageId() {
		return groupImageId;
	}

	public void setGroupImageId(long groupImageId) {
		this.groupImageId = groupImageId;
	}
}