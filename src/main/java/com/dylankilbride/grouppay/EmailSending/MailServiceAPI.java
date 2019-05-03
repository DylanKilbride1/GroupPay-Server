package com.dylankilbride.grouppay.EmailSending;

import com.dylankilbride.grouppay.Models.Mail;

public interface MailServiceAPI {

	public void sendEmailTransactionStatement(Mail transactionStatement);
}
