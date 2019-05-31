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

	public void createIncomingPaymentTransactionRecord(String userId, String groupId, double amountToDebit, double amountForGroup) {
		Transaction newIncomingTransactionPaymentRecord = new Transaction(convertDoubleToBigDecimal(amountForGroup),
						calculateGroupPayFees(convertDoubleToBigDecimal(amountForGroup), convertDoubleToBigDecimal(amountToDebit)),
						"Incoming",
						getFormattedPaymentDateAndTime(Calendar.getInstance()),
						userRepository.findUsersById(Long.valueOf(userId)).getFullName(),
						groupAccountRepository.findByGroupAccountId(Long.valueOf(groupId)).getAccountName());
		newIncomingTransactionPaymentRecord.setUserId(Long.valueOf(userId));
		newIncomingTransactionPaymentRecord.setGroupId(Long.valueOf(groupId));
		transactionRepository.save(newIncomingTransactionPaymentRecord);
	}

	public List<Transaction> getTransactionsForGroupAccount(long groupAccountId){
		return transactionRepository.findTransactionByGroupId(groupAccountId);
	}

	public List<Transaction> getUserTransactionHistory(long userId) {
		return transactionRepository.findTransactionByUserId(userId);
	}

	private BigDecimal convertDoubleToBigDecimal(double amount){
		return BigDecimal.valueOf(amount);
	}

	public String getFormattedPaymentDateAndTime(Calendar paymentDateTime) {
		paymentDateTime.add(Calendar.DATE, 1);
		SimpleDateFormat format = new SimpleDateFormat("dd MMM, H:mm");
		return format.format(paymentDateTime.getTime());
	}

	public BigDecimal calculateGroupPayFees(BigDecimal amountForGroup, BigDecimal amountDebited) {
		BigDecimal stripePercentage = new BigDecimal(1.019);
		BigDecimal stripeFixedFee = new BigDecimal(.25);
		BigDecimal amountPlusStripe = amountForGroup.multiply(stripePercentage).add(stripeFixedFee);
		return amountDebited.subtract(amountPlusStripe);
	}
}
