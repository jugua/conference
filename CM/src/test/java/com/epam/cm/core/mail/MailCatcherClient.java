package com.epam.cm.core.mail;

import java.util.*;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.type.TypeReference;

import static com.epam.cm.core.json.JsonUtil.fromJSON;
import static com.epam.cm.core.properties.PropertiesNames.*;

public class MailCatcherClient {

    // private static final int SMTP_PORT = 1025;
    private static final int SMTP_PORT = Integer.valueOf(System.getProperty(MAIL_CATCHER_SMTP_PORT, "1025"));
    private static final int HTTP_RESPONSE_OK = 200;
    private static final int HTTP_RESPONSE_NO_CONTENT = 204;
    private static final Logger LOGGER = Logger.getLogger(MailCatcherClient.class.getName());
    private Client client;
    private WebTarget service;
    private String smtpIP;
    public MailCatcherClient() {
        smtpIP = System.getProperty(MAIL_CATCHER_IP);
        String httpPort = System.getProperty(MAIL_CATCHER_HTTP_PORT);
        client = ClientBuilder.newBuilder().build();
        service = client.target("http://" + smtpIP + ":" + httpPort);
    }

    public MailCatcherClient(final String ip, final int httpPort) {
        smtpIP = ip;
        client = ClientBuilder.newBuilder().build();
        service = client.target("http://" + smtpIP + ":" + httpPort);
    }

    public void disconnect() {
        client.close();
    }

    public Object getEmailById(final int id, final ResponseType responseType) {
        final Response response = service.path("messages").path(id + "." + responseType.name().toLowerCase())
                .request(MediaType.APPLICATION_JSON).get();

        final String entity = response.readEntity(String.class);

        return response.getStatus() == HTTP_RESPONSE_OK && responseType == ResponseType.JSON
                ? fromJSON(new TypeReference<Email>() {
                }, entity) : entity;
    }

    public Email getEmailByIndex(final int index) {
        final List<Email> emails = getEmails();
        return emails != null && emails.size() > 0 ? emails.get(index >= 0 && index < emails.size() ? index : 0) : null;
    }

    public Email getLastEmail() {
        return getEmailByIndex(0);
    }

    public List<Email> getEmails() {
        final List<Email> emails = fromJSON(new TypeReference<List<Email>>() {
        }, service.path("messages").request(MediaType.APPLICATION_JSON).get().readEntity(String.class));

        Collections.sort(emails, new Comparator<Email>() {
            @Override
            public int compare(Email email1, Email email2) {
                if (email1.getId() < email2.getId()) {
                    return 1;
                } else if (email1.getId() > email2.getId()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        return emails;
    }

    public boolean deleteEmails() {
        return service.path("messages").request(MediaType.APPLICATION_JSON).delete()
                .getStatus() == HTTP_RESPONSE_NO_CONTENT;
    }

    public int getEmailsAmount() {
        final List<Email> emails = getEmails();
        return emails != null ? emails.size() : 0;
    }

    public void sendEmail(final String from, final String to, final String subject, final String body,
            final ContentType contentType) {
        final Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpIP);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.transport.protocol", "smtp");

        final Session session = Session.getInstance(properties);
        sendEmail(session, from, to, subject, body, contentType);
    }

    private void sendEmail(final Session session, final String from, final String to, final String subject,
            final String body, final ContentType contentType) {
        try {
            final Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setSentDate(new Date());

            final MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText(body, "utf-8", contentType.name().toLowerCase());

            final Multipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(mimeBodyPart);
            message.setContent(multiPart);
            Transport.send(message);
        } catch (final Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public enum ResponseType {
        HTML, PLAIN, JSON, SOURCE
    }

    public enum ContentType {
        HTML, PLAIN
    }
}
