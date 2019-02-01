package com.dylankilbride.grouppay.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "account_id")
	private int accountId;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "account_iban")
	private String accountIban;

	@Column(name = "account_bic")
	private String accountBic;

	@OneToMany(mappedBy = "bankAccount")
	private Set<Transaction> transaction;
}
