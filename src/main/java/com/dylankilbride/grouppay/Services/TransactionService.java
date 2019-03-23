package com.dylankilbride.grouppay.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

	@Autowired

	public void createPaymentTransactionRecord(String userId, String groupId, double amount) {

	}

	private BigDecimal convertDoubleToBigDecimal(double amount){
		return BigDecimal.valueOf(amount);
	}
}
