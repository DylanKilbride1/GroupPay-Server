package com.dylankilbride.grouppay.EmailSending;

import com.dylankilbride.grouppay.Models.Mail;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService implements MailServiceAPI {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Configuration freemarkerConfiguration;

	@Override
	public void sendEmailTransactionStatement(Mail transactionStatement) {

	}

}
