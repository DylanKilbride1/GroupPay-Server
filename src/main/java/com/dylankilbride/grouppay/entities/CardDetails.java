package com.dylankilbride.grouppay.entities;

import javax.persistence.*;

@Table(name = "CardDetails", schema = "grouppay")

//TODO @Entity
public class CardDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "card_id")
	private int cardId;
	@Column(name = "card_number")
	private String cardNumber;
	@Column(name = "expiry_date")
	private String expiryDate;
	@Column(name = "cardholder_name")
	private String cardHolderName;

	public CardDetails(String cardNumber, String expiryDate, String cardHolderName) {
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.cardHolderName = cardHolderName;
	}

	public CardDetails() {
		//Empty Constructor
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
}