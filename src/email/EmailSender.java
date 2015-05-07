package email;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {
	
	private Session session = null;
	private Message message = null;
	private Multipart multipart = null;
	
	public static void main(String[] args) {
		
		String FROM = "henry.v.h.l@gmail.com";
		String TO = "chang123yfde@gmail.com";
		try {
			EmailSender.createEmail(FROM, "password", TO, "Test JavaMail")
				.withMessage("Test if I can send an Email")
				.withFile("/Users/Lijia/Desktop/", "Email Code.pdf")
				.send();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* Constructor: Set Email username and password */
	private EmailSender(final String user, final String pwd) {
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.port", "587");   //Port for TLS/STARTTLS
		
		session = Session.getInstance(
			props, 
			new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, pwd);
				}
			}
		);
		
	}
	
	/* Create an Email with Recipients and Sender */
	public static EmailSender createEmail(String fromAddr, String pwd, String toAddr, String subject) throws AddressException, MessagingException {
		
		EmailSender sender = new EmailSender(fromAddr, pwd);
		
		sender.message = new MimeMessage(sender.session);
		sender.message.setFrom(new InternetAddress(fromAddr));
		sender.message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));
		sender.message.setSubject(subject);
		
		sender.multipart = new MimeMultipart();
		
		return sender;
	}
	
	/* Write Body Message */
	public EmailSender withMessage(String msg) throws MessagingException {
		
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(msg);
		multipart.addBodyPart(messageBodyPart);
		
		return this;
	}
	
	/* Attach Files */
	public EmailSender withFile(String folder, String name) throws MessagingException {
		
		DataSource source = new FileDataSource(folder + "/" + name);
		BodyPart fileBodyPart = new MimeBodyPart();
		fileBodyPart.setDataHandler(new DataHandler(source));
		fileBodyPart.setFileName(name);
		multipart.addBodyPart(fileBodyPart);
		
		return this;
	}
	
	/* Send Email */
	public void send() throws MessagingException {
		
		message.setContent(multipart);
		Transport.send(message);
		System.out.println("Email sent.");
		
	}
	
}
