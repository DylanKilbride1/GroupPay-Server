package com.dylankilbride.grouppay.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "groupAccount_users",
					joinColumns = {@JoinColumn(name = "groupAccount_id")},
					inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private List<GroupAccount> groupAccounts = new ArrayList<>();

	@Column(name = "stripe_customer_id")
	private String stripeCustomer;

	@JsonIgnore
	private int numberOfPaymentMethods;

	@Column(name = "device_token")
	private String deviceToken;

	private String isUserVerified = "FALSE";

	public User(String firstName, String lastName, String emailAddress, String password, String mobileNumber, String deviceToken) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.deviceToken = deviceToken;
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

	public String getStripeCustomerId() {
		return stripeCustomer;
	}

	public void setStripeCustomerId(String stripeCustomer) {
		this.stripeCustomer = stripeCustomer;
	}

	public int getNumberOfPaymentMethods() {
		return numberOfPaymentMethods;
	}

	public void addPaymentMethod() {
		numberOfPaymentMethods++;
	}

	public void removePaymentMethod() {
		numberOfPaymentMethods--;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getIsUserVerified() {
		return isUserVerified;
	}

	public void setIsUserVerified(String isUserVerified) {
		this.isUserVerified = isUserVerified;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}
}