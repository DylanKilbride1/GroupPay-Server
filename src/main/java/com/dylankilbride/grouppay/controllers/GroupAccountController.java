package com.dylankilbride.grouppay.controllers;

import com.dylankilbride.grouppay.models.GroupAccount;
import com.dylankilbride.grouppay.services.GroupAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groupAccounts")
public class GroupAccountController {

	@Autowired
	GroupAccountService groupAccountService;

	@RequestMapping(value = "/createBasicAccount",
					method = RequestMethod.PUT,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GroupAccount createBasicGroupAccount(@RequestBody GroupAccount groupAccount) {
		return groupAccountService.createBasicGroupAccount(groupAccount);
	}
}
