package com.dylankilbride.grouppay.Models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
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
	private String paymentDateAndTime;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private GroupAccount groupAccount;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "bank_account")
	private BankAccount bankAccount;

	public Transaction(BigDecimal amountPaid, String paymentType, String paymentDateAndTime) {
		this.amountPaid = amountPaid;
		this.paymentType = paymentType;
		this.paymentDateAndTime = paymentDateAndTime;
	}

	public Transaction(){

	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentDateAndTime() {
		return paymentDateAndTime;
	}

	public String getFormattedPaymentDateAndTime(Calendar paymentDateTime) {
		paymentDateTime.add(Calendar.DATE, 1);
		SimpleDateFormat format = new SimpleDateFormat("dd MMM, H:mm");
		return format.format(paymentDateTime.getTime());
	}

	public void setPaymentDateAndTime(String paymentDateAndTime) {
		this.paymentDateAndTime = paymentDateAndTime;
	}

	public void setGroupAccount(GroupAccount groupAccount) {
		this.groupAccount = groupAccount;
	}

	public GroupAccount getGroupAccount() {
		return groupAccount;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}
}