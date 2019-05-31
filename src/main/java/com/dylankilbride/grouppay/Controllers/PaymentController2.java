package com.dylankilbride.grouppay.Controllers;

import com.dylankilbride.grouppay.Models.StripeCharge;
import com.dylankilbride.grouppay.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController2 {

	@Autowired
	PaymentService paymentService;

	@RequestMapping(value = "/oneTimePayment",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity makeOneTimePayment(@RequestBody StripeCharge chargeDetails) {
		return paymentService.makeOneTimePaymentCharge(chargeDetails);
	}

	@RequestMapping(value = "/saveCard",
					method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity savePaymentCardDetails(@RequestBody StripeCharge customerDetails) {
		return savePaymentCardDetails(customerDetails);
	}
}