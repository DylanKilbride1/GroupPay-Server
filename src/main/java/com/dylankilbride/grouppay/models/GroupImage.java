package com.dylankilbride.grouppay.models;

import javax.persistence.*;

@Entity
public class GroupImage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "group_image_id")
	private long groupImageId;

	@Column(name = "group_image_location")
	private String groupImageLocation;

	@OneToOne(fetch = FetchType.LAZY,
					cascade =  CascadeType.ALL,
					mappedBy = "groupImage")
	private GroupAccount groupAccount;
}