package com.dylankilbride.grouppay.controllers;

import com.dylankilbride.grouppay.models.Contact;
import com.dylankilbride.grouppay.models.GroupAccount;
import com.dylankilbride.grouppay.services.GroupAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@RequestMapping(value = "/addParticipants/{groupAccountId}",
					method = RequestMethod.PUT,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GroupAccount addParticipantsToAccount(@PathVariable(name = "groupAccountId") long groupAccountId,
	                                             @RequestBody List<Contact> contacts) {
		return groupAccountService.addParticipantsToGroupAccount(groupAccountId, contacts);
	}

	@RequestMapping(value = "/getDetailedGroupInfo/{groupAccountId}",
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GroupAccount getDetailedGroupInfo(@PathVariable(name = "groupAccountId") long groupAccountId) {
		return groupAccountService.getDetailedGroupAccountInfo(groupAccountId);
	}
}