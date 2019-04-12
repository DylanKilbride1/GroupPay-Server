package com.dylankilbride.grouppay.Models;

public class PaymentDetails {

	private String lastFour;
	private int expiryMonth;
	private int expiryYear;

	public PaymentDetails(String lastFour, int expiryMonth, int expiryYear) {
		this.lastFour = lastFour;
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
	}

	public PaymentDetails() {}

	public String getLastFour() {
		return lastFour;
	}

	public void setLastFour(String lastFour) {
		this.lastFour = lastFour;
	}

	public int getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public int getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}
}
