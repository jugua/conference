package com.epam.cm.core.mail;



import org.junit.Test;

import javax.mail.internet.ContentType;

import static com.epam.cm.core.mail.MailCatcherClient.*;

/**
 * Author: Serhii Kuts
 * Date: 9/6/2014
 * Time: 6:36 PM
 */
public class MailCatcherTest {

    private static final String PLAIN_TEXT_BODY =
            "Hello, \nThis message was sent using Notification Service.\n\nThanks.\n\nP.S. Please ignore this message.";

    private static final String HTML_BODY =
            "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\">\n" +
                    "</head>\n" +
                    "<body bgcolor=\"#ffffff\" text=\"#000000\">\n" +
                    "Hello,<br>This message was sent using Notification Service.<br><br>\n" +
                    "Thanks.<br><br>P.S. Please ignore this message.\n" +
                    "</body>\n" +
                    "</html>";

    private static final String SUBJECT = "AT-INFO-Notification";

    @Test
    public void emailsProcessing() {
        final MailCatcherClient client = new MailCatcherClient("10.17.132.37", 1080);

//        client.sendEmail("notifier@at.info", "recipient1@at.info", SUBJECT, PLAIN_TEXT_BODY, MailCatcherClient.ContentType.PLAIN);
//        client.sendEmail("notifier@gmail.com", "recipient1@at.info,recipient2@at.info", SUBJECT, HTML_BODY, MailCatcherClient.ContentType.HTML);
//        System.out.println("Emails amount: " + client.getEmailsAmount());
//        System.out.println("Emails list: " + client.getEmails());
//        System.out.println("Last email: " + client.getLastEmail());
//        System.out.println("Email with index 1: " + client.getEmailByIndex(1));
//        System.out.println("JSON of an email with index 1: " + client.getEmailById(1, ResponseType.JSON));
        System.out.println(client.getEmailById(client.getLastEmail().getId(), ResponseType.HTML).toString());
//        System.out.println("First email's plain text body: " + client.getEmailById(1, ResponseType.PLAIN));
//        System.out.println("Last email's source: " + client.getEmailById(2, ResponseType.SOURCE));
//        System.out.println("Deleting emails: " + client.deleteEmails());
//        System.out.println("Emails amount: " + client.getEmailsAmount());
        client.disconnect();
    }
}