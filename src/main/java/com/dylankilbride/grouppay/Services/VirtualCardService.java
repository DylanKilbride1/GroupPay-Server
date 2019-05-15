package com.dylankilbride.grouppay.Services;

import com.dylankilbride.grouppay.Models.GroupAccount;
import com.dylankilbride.grouppay.Models.VirtualCard;
import com.dylankilbride.grouppay.Repositories.GroupAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class VirtualCardService {

	List<String> mod10VirtualPans = new ArrayList<>();

	@Autowired
	GroupAccountRepository groupAccountRepository;

	public VirtualCardService() {
		mod10VirtualPans.add("5163698397757686");
		mod10VirtualPans.add("5266100265870358");
		mod10VirtualPans.add("5204278358741648");
		mod10VirtualPans.add("5446952376388560");
		mod10VirtualPans.add("5514272115318191");
		mod10VirtualPans.add("5374095440965991");
	}

	private String generateRandomCVV() {
		Random random3Digits = new Random();
		return String.valueOf((random3Digits.nextInt(999 - 111) + 1) + 100);
	}

	private String generateRandomExpiryDate() throws ParseException {
		LocalDate todaysDate = LocalDate.now();
		LocalDate maxExpiryDate = LocalDate.of(2025, 12, 30);
		long now = todaysDate.toEpochDay();
		long max = maxExpiryDate.toEpochDay();
		long randomExpiry = ThreadLocalRandom.current().longs(now, max).findAny().getAsLong();

		SimpleDateFormat newFormat = new SimpleDateFormat("MM/yy");
		SimpleDateFormat dateToParse = new SimpleDateFormat("yyyy-MM-dd");

		return newFormat.format(dateToParse.parse(LocalDate.ofEpochDay(randomExpiry).toString()));
	}

	public VirtualCard generateRandomVirtualCardDetails(String groupAccountId) throws ParseException {
		long id = Long.valueOf(groupAccountId);
		GroupAccount tempGroup = groupAccountRepository.findByGroupAccountId(id);

		String cvv = generateRandomCVV();
		String expiryDate = generateRandomExpiryDate();
		String pan = mod10VirtualPans.get(new Random().nextInt(mod10VirtualPans.size()));
		String country = "Ireland";
		String issuer = "Mastercard";

		VirtualCard issuedCard = new VirtualCard(pan, expiryDate, country, cvv, issuer);
		tempGroup.setVirtualCard(issuedCard);
		groupAccountRepository.save(tempGroup);

		return issuedCard;
	}



}
