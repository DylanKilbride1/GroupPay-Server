package com.dylankilbride.grouppay.Clients;

import com.dylankilbride.grouppay.Models.StripeChargeReceipt;
import com.dylankilbride.grouppay.Services.TransactionService;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

	public StripeChargeReceipt chargePaymentCard(String token, double amount, String userId, String groupAccountId){
		try {

			Map<String, Object> chargeParameters = new HashMap<>();
			chargeParameters.put("amount", (int) (amount * 100));
			chargeParameters.put("currency", "EUR");
			chargeParameters.put("source", token);
			Charge charge = Charge.create(chargeParameters);

			transactionService.createPaymentTransactionRecord(userId, groupAccountId, amount);

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
			return new StripeChargeReceipt(0L,
							"999",
							"Exception!",
							"Exception was caught by server");
		}
	}
}