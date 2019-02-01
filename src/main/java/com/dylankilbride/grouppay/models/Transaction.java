package com.dylankilbride.grouppay.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "transaction_id")
	private long transactionId;

	@Column(name = "amount_paid")
	private BigDecimal amountPaid;

	@Column(name = "paymentType")
	private String paymentType;

	@Column(name = "payment_date_time")
	private Date paymentDateAndTime;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private GroupAccount groupAccount;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "bank_account")
	private BankAccount bankAccount;
}