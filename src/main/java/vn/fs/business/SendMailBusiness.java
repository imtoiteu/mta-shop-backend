package vn.fs.business;

import java.io.IOException;

import javax.mail.MessagingException;

import vn.fs.dto.MailInfo;

public interface SendMailBusiness {

	void run();

	void queue(String to, String subject, String body);

	void queue(MailInfo mail);

	void send(MailInfo mail) throws MessagingException, IOException;

}
