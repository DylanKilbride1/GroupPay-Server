package com.dylankilbride.grouppay.Services;

import com.dylankilbride.grouppay.Models.GroupAccount;
import com.dylankilbride.grouppay.Models.Transaction;
import com.dylankilbride.grouppay.Repositories.GroupAccountRepository;
import com.dylankilbride.grouppay.Repositories.TransactionRepository;
import com.dylankilbride.grouppay.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

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
						getFormattedPaymentDateAndTime(Calendar.getInstance()));
		newIncomingTransactionPaymentRecord.setUser(userRepository.findUsersById(Long.valueOf(userId)));
		newIncomingTransactionPaymentRecord.setGroupAccount(groupAccountRepository.findByGroupAccountId(Long.valueOf(groupId)));
		transactionRepository.save(newIncomingTransactionPaymentRecord);
	}

	public List<Transaction> getTransactionsForGroupAccount(long groupAccountId){
		return transactionRepository.findTransactionByGroupAccount(groupAccountRepository.findByGroupAccountId(groupAccountId));
	}

	private BigDecimal convertDoubleToBigDecimal(double amount){
		return BigDecimal.valueOf(amount);
	}

	public String getFormattedPaymentDateAndTime(Calendar paymentDateTime) {
		paymentDateTime.add(Calendar.DATE, 1);
		SimpleDateFormat format = new SimpleDateFormat("dd MMM, H:mm");
		return format.format(paymentDateTime.getTime());
	}
}
