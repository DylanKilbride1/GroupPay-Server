package com.dylankilbride.grouppay.Services;

import com.dylankilbride.grouppay.Models.GroupAccount;
import com.dylankilbride.grouppay.Models.StripeCharge;
import com.dylankilbride.grouppay.Models.User;
import com.dylankilbride.grouppay.Repositories.GroupAccountRepository;
import com.dylankilbride.grouppay.Repositories.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

	@Autowired
	TransactionService transactionService;

	@Autowired
	GroupAccountRepository groupAccountRepository;

	@Autowired
	UserRepository userRepository;

	public PaymentService(@Value("${stripe.key.secret}") String secretKey) {
		Stripe.apiKey = secretKey;
	}

	public ResponseEntity makeOneTimePaymentCharge(StripeCharge chargeDetails) {
		try {
			Map<String, Object> chargeParams = new HashMap<>();
			chargeParams.put("amount", (int) (chargeDetails.getAmountInclFees() * 100));
			chargeParams.put("currency", "eur");
			chargeParams.put("source", chargeDetails.getTokenId());

			Charge.create(chargeParams);
			createTransactionRecord(chargeDetails);
			updateGroupPaymentStatus(chargeDetails);

			return new ResponseEntity(HttpStatus.OK);

		} catch (APIConnectionException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		} catch (APIException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		} catch (CardException e) {
			e.printStackTrace();
			e.getCode();
			e.getMessage();
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	private void createTransactionRecord(StripeCharge chargeDetails) {
		transactionService.createIncomingPaymentTransactionRecord(chargeDetails.getUserId(),
						chargeDetails.getGroupAccountId(),
						chargeDetails.getAmountInclFees(),
						chargeDetails.getAmountForGroup());
	}

	private void updateGroupPaymentStatus(StripeCharge chargeDetails) {
		GroupAccount accountToModify = groupAccountRepository.findByGroupAccountId(Long.valueOf(chargeDetails.getGroupAccountId()));
		accountToModify.updateAmountPaid(BigDecimal.valueOf(chargeDetails.getAmountForGroup()));
		groupAccountRepository.save(accountToModify);
	}

	public ResponseEntity savePaymentDetails(StripeCharge customerDetails) {
		try{
			if(getUserById(Long.valueOf(customerDetails.getUserId())).getStripeCustomerId().equals("")) {
				createStripeCustomer(customerDetails);
				return new ResponseEntity(HttpStatus.OK);
			} else {
				if(addCardToStripeCustomer(Long.valueOf(customerDetails.getUserId(),
								customerDetails.getTokenId()))) {
					return new ResponseEntity(HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean createStripeCustomer(StripeCharge customerDetails) {
		try {
			Map<String, Object> customerParams = new HashMap<>();
			customerParams.put("source", customerDetails.getTokenId());
			customerParams.put("email", getUserById(Long.valueOf(customerDetails.getUserId())).getEmailAddress());

			Customer customer = Customer.create(customerParams);

			updateUsersStripeCustomerId(getUserById(Long.valueOf(customerDetails.getUserId())),
							customer.getId());

			return true;
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			return false;
		} catch (APIConnectionException e) {
			e.printStackTrace();
			return false;
		} catch (CardException e) {
			e.printStackTrace();
			return false;
		} catch (APIException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean addCardToStripeCustomer(long userId, String tokenId) {
		try {
			User user = getUserById(userId);

			Customer stripeCustomer = Customer.retrieve(user.getStripeCustomerId());
			Map<String, Object> params = new HashMap<>();
			params.put("source", tokenId);
			stripeCustomer.getSources().create(params);

			return true;
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			return false;
		} catch (APIConnectionException e) {
			e.printStackTrace();
			return false;
		} catch (CardException e) {
			e.printStackTrace();
			return false;
		} catch (APIException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private User getUserById(long userId) {
		return userRepository.findUsersById(userId);
	}

	private void updateUsersStripeCustomerId(User user, String stripeCustomerId) {
		user.setStripeCustomerId(stripeCustomerId);
		userRepository.save(user);
	}
}