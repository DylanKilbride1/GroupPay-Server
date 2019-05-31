package com.dylankilbride.grouppay.Services;

import com.dylankilbride.grouppay.Models.GroupAccount;
import com.dylankilbride.grouppay.Models.VirtualCard;
import com.dylankilbride.grouppay.Repositories.GroupAccountRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Random;

@Service
public class VirtualCardService {

	ArrayList<VirtualCard> cards = new ArrayList<>();

	@Autowired
	GroupAccountRepository groupAccountRepository;

	public void getVirtualCardsList() {
		final String jsonBlobUrl = "https://jsonblob.com/api/jsonBlob/1feadaf0-808d-11e9-8ec2-d357c32e48ca";
		RestTemplate restTemplate = new RestTemplate();
		String cards = restTemplate.getForObject(jsonBlobUrl, String.class);
		parseCardsJson(cards);
	}

	private void parseCardsJson(String cardsJson) {
		JSONArray cardsList = new JSONArray(cardsJson);
		for (int i = 0; i < cardsList.length(); i++) {
			JSONObject container = cardsList.getJSONObject(i);
			JSONObject cardDetails = container.getJSONObject("CreditCard");
			addIndividualCardsToList(new VirtualCard(cardDetails.getString("CardNumber"),
							cardDetails.getString("Exp"),
							"Ireland",
							cardDetails.getString("CVV"),
							cardDetails.getString("IssuingNetwork")));
		}
	}

	private void addIndividualCardsToList(VirtualCard virtualCard) {
			cards.add(virtualCard);
	}

	public void assignVirtualCardToGroup(long groupId) {
		Random randomCardSelection = new Random();
		getVirtualCardsList();
		GroupAccount groupAccount = groupAccountRepository.findByGroupAccountId(groupId);
		VirtualCard cardToAssign = cards.get(randomCardSelection.nextInt(cards.size()));
		while (!mod10Check(cardToAssign.getPan())) {
			cardToAssign = cards.get(randomCardSelection.nextInt(cards.size()));
		}
		groupAccount.setVirtualCard(cards.get(randomCardSelection.nextInt(cards.size())));
		groupAccountRepository.save(groupAccount);
	}

	public VirtualCard getVirtualCardDetails(String groupAccountId) {
		GroupAccount group = groupAccountRepository.findByGroupAccountId(Long.valueOf(groupAccountId));
		return group.getVirtualCard();
	}

	private static boolean mod10Check(String cardNumber) {
		int sum = 0;
		boolean alternate = false;
		for (int i = cardNumber.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(cardNumber.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}
		return (sum % 10 == 0);
	}
}