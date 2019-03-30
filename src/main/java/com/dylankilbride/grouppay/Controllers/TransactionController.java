package com.dylankilbride.grouppay.Controllers;

import com.dylankilbride.grouppay.Models.GroupAccount;
import com.dylankilbride.grouppay.Models.Transaction;
import com.dylankilbride.grouppay.Repositories.TransactionRepository;
import com.dylankilbride.grouppay.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@RequestMapping(value = "/getUsersTransactionHistory/{userId}",
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Transaction> getAllUsersTransactions(@PathVariable(name = "userId") long userId) {
		return transactionService.getUserTransactionHistory(userId);
	}
}
