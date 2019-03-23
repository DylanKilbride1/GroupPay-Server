package com.dylankilbride.grouppay.Controllers;

import com.dylankilbride.grouppay.Clients.StripeClient;
import com.dylankilbride.grouppay.Models.StripeCharge;
import com.dylankilbride.grouppay.Models.StripeChargeReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	private StripeClient stripeClient;

	@Autowired
	public PaymentController(StripeClient stripeClient) {
		this.stripeClient = stripeClient;
	}

	@RequestMapping(value = "/charge",
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public StripeChargeReceipt chargeCard(@RequestBody StripeCharge stripeCharge) throws Exception {
		return this.stripeClient.chargePaymentCard(stripeCharge.getTokenId(),
						stripeCharge.getAmount(),
						stripeCharge.getUserId(),
						stripeCharge.getGroupAccountId());
	}
}
