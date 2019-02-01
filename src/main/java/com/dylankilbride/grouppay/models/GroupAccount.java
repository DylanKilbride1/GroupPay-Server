package com.dylankilbride.grouppay.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class GroupAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "account_id")
	private long groupAccountId;

	@Column(name = "account_name")
	private String accountName;

	@Column(name = "account_description")
	private String accountDescription;

	@Column(name = "number_of_members")
	private int numberOfMembers;

	@Column(name = "amount_owed")
	private BigDecimal totalAmountOwed;

	@Column(name = "amount_paid")
	private BigDecimal totalAmountPaid;

	@OneToMany(mappedBy = "groupAccount")
	private Set<Transaction> transaction;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_image")
	private GroupImage groupImage;

	@ManyToMany
	@JoinTable(name = "groupAccount_users",
					joinColumns = {@JoinColumn(name = "groupAccount_id")},
					inverseJoinColumns = {@JoinColumn(name = "user_id")})
	private Set<User> groupMembers;

}