package com.dylankilbride.grouppay.Clients;

import com.dylankilbride.grouppay.Models.GroupAccount;
import com.dylankilbride.grouppay.Models.StripeChargeReceipt;
import com.dylankilbride.grouppay.Models.User;
import com.dylankilbride.grouppay.Repositories.GroupAccountRepository;
import com.dylankilbride.grouppay.Repositories.UserRepository;
import com.dylankilbride.grouppay.Services.TransactionService;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class StripeClient {

	@Autowired
	public StripeClient(@Value("${stripe.key.secret}") String secretKey) {
		Stripe.apiKey = secretKey;
	}

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private GroupAccountRepository groupAccountRepository;
	@Autowired
	private UserRepository userRepository;

	public StripeChargeReceipt chargePaymentCard(String token, double amount, String userId, String groupAccountId) {
		try {
			//TODO Add check for valid user and group IDs

			Map<String, Object> chargeParameters = new HashMap<>();
			chargeParameters.put("amount", (int) (amount * 100));
			chargeParameters.put("currency", "EUR");
			chargeParameters.put("source", token);
			Charge charge = Charge.create(chargeParameters);

			transactionService.createIncomingPaymentTransactionRecord(userId, groupAccountId, amount); //Crash if userId doesnt exist
			GroupAccount accountToModify = groupAccountRepository.findByGroupAccountId(Long.valueOf(groupAccountId));
			accountToModify.updateAmountPaid(BigDecimal.valueOf(amount));
			groupAccountRepository.save(accountToModify);

			return new StripeChargeReceipt(charge.getAmount(),
							charge.getFailureCode(),
							charge.getFailureMessage(),
							charge.getDescription());

		} catch (RateLimitException e) {
			return new StripeChargeReceipt(0L,
							"999",
							"Exception!",
							"Exception was caught by server");
		} catch (InvalidRequestException e) {
			return new StripeChargeReceipt(0L,
							"999",
							"Exception!",
							"Exception was caught by server");
		} catch (AuthenticationException e) {
			return new StripeChargeReceipt(0L,
							"999",
							"Exception!",
							"Exception was caught by server");
		} catch (APIConnectionException e) {
			return new StripeChargeReceipt(0L,
							"999",
							"Exception!",
							"Exception was caught by server");
		} catch (StripeException e) {
			return new StripeChargeReceipt(0L,
							"999",
							"Exception!",
							"Exception was caught by server");
		} catch (Exception e) {
			System.out.println(e.fillInStackTrace());
			return new StripeChargeReceipt(0L,
							"999",
							"Exception!",
							"Exception was caught by server");
		}
	}

	public String createStripeCustomer(String token, String userId){
		User user = userRepository.findUsersById(Long.valueOf(userId));
		Map<String, Object> customerParams = new HashMap<>();
		customerParams.put("source", token);
		customerParams.put("email", user.getEmailAddress());
		try {
			if(user.getStripeCustomer() == null) {
				Customer customer = Customer.create(customerParams);
				user.setStripeCustomer(customer);
				return "success";
			}
			else {
				return "success";
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return "failure";
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			return "failure";
		} catch (APIConnectionException e) {
			e.printStackTrace();
			return "failure";
		} catch (CardException e) {
			e.printStackTrace();
			return "failure";
		} catch (APIException e) {
			e.printStackTrace();
			return "failure";
		}
	}

	public StripeChargeReceipt chargeStripeCustomer(String userId, double amount, String groupAccountId) {
		User user = userRepository.findUsersById(Long.valueOf(userId));
		Map<String, Object> customerParams = new HashMap<>();
		customerParams.put("amount", amount);
		customerParams.put("currency", "eur");
		customerParams.put("customer", user.getStripeCustomer().getId());
		try {
			Charge charge = Charge.create(customerParams);

			transactionService.createIncomingPaymentTransactionRecord(userId, groupAccountId, amount); //Crash if userId doesnt exist
			GroupAccount accountToModify = groupAccountRepository.findByGroupAccountId(Long.valueOf(groupAccountId));
			accountToModify.updateAmountPaid(BigDecimal.valueOf(amount));
			groupAccountRepository.save(accountToModify);

			return new StripeChargeReceipt(charge.getAmount(),
							charge.getFailureCode(),
							charge.getFailureMessage(),
							charge.getDescription());

		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new StripeChargeReceipt(0L,
							"999",
							"Exception!",
							"Exception was caught by server");
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			return new StripeChargeReceipt(0L,
							"999",
							"Exception!",
							"Exception was caught by server");
		} catch (APIConnectionException e) {
			e.printStackTrace();
			return new StripeChargeReceipt(0L,
							"999",
							"Exception!",
							"Exception was caught by server");
		} catch (CardException e) {
			e.printStackTrace();
			return new StripeChargeReceipt(0L,
							"999",
							"Exception!",
							"Exception was caught by server");
		} catch (APIException e) {
			e.printStackTrace();
			return new StripeChargeReceipt(0L,
							"999",
							"Exception!",
							"Exception was caught by server");
		}
	}
}