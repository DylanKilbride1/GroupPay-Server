package com.dylankilbride.grouppay.Models;

import com.stripe.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Table(name = "User", schema = "grouppay")

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "user_id")
	private long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email_address")
	private String emailAddress;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@OneToOne(fetch = FetchType.EAGER,
	cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_image")
	private ProfileImage profileImage;

	@OneToMany(fetch = FetchType.EAGER,
	cascade = CascadeType.ALL,
	mappedBy = "user")
	private Set<Transaction> transaction;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "groupAccount_users",
					joinColumns = {@JoinColumn(name = "groupAccount_id")},
					inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private List<GroupAccount> groupAccounts = new ArrayList<>();

	@OneToOne(fetch = FetchType.EAGER,
					cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private Customer stripeCustomer;

	public User(String firstName, String lastName, String emailAddress, String password, String mobileNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.password = password;
		this.mobileNumber = mobileNumber;
	}

	public User() {
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public List<GroupAccount> getUsersAccounts() {
		return groupAccounts;
	}

	public ProfileImage getProfileImage() {
		if(profileImage != null) {
			return profileImage;
		} else {
			return null;
		}
	}

	public void setProfileImage(ProfileImage profileImage) {
		this.profileImage = profileImage;
	}

	public Customer getStripeCustomer() {
		return stripeCustomer;
	}

	public void setStripeCustomer(Customer stripeCustomer) {
		this.stripeCustomer = stripeCustomer;
	}
}