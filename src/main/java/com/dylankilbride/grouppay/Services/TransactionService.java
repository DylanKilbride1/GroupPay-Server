package com.dylankilbride.grouppay.Services;

import com.dylankilbride.grouppay.Models.GroupAccount;
import com.dylankilbride.grouppay.Models.Transaction;
import com.dylankilbride.grouppay.Repositories.GroupAccountRepository;
import com.dylankilbride.grouppay.Repositories.TransactionRepository;
import com.dylankilbride.grouppay.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	GroupAccountRepository groupAccountRepository;
	@Autowired
	TransactionRepository transactionRepository;

	public void createIncomingPaymentTransactionRecord(String userId, String groupId, double amount) {
		Transaction newIncomingTransactionPaymentRecord = new Transaction(convertDoubleToBigDecimal(amount),
						"Incoming",
						LocalDateTime.now());
		newIncomingTransactionPaymentRecord.setUser(userRepository.findUsersById(Long.valueOf(userId)));
		newIncomingTransactionPaymentRecord.setGroupAccount(groupAccountRepository.findByGroupAccountId(Long.valueOf(groupId)));
		transactionRepository.save(newIncomingTransactionPaymentRecord);
	}

	private BigDecimal convertDoubleToBigDecimal(double amount){
		return BigDecimal.valueOf(amount);
	}
}
