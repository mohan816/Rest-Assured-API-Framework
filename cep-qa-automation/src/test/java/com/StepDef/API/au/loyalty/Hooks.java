package com.StepDef.API.au.loyalty;


import com.utils.Basecode;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.AfterAll;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Hooks extends Basecode {
    private Basecode basecode;

    public Hooks(Basecode basecode) throws ConfigurationException {
        super();
        this.basecode = basecode;
    }

    @AfterAll
    static void sendMail() {
        // Create object of Property file
        Properties props = new Properties();
        // this will set host of server- you can change based on your requirement
        props.put("mail.smtp.host", "smtp.gmail.com");
        // set the port of socket factory
        props.put("mail.smtp.socketFactory.port", "465");
        // set socket factory
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // set the authentication to true
        props.put("mail.smtp.auth", "true");
        // set the port of SMTP server
        props.put("mail.smtp.port", "465");
        // This will handle the complete authentication
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("add your email", "add your password");
                    }
                });

        try {
            // Create object of MimeMessage class
            Message message = new MimeMessage(session);
            // Set the from address
            message.setFrom(new InternetAddress("qaaurewards01@gmail.com"));
            // Set the recipient address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("Dhanasekaran.Arthanari.Contractor@pepsico.com"));
            // Add the subject link
            message.setSubject("AU Rewards API Automation Report");
            // Create object to add multimedia type content
            BodyPart messageBodyPart1 = new MimeBodyPart();
            // Set the body of email
            messageBodyPart1.setText("Kindly find the AU Rewards API Automation Report");
            // Create another object to add another content
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            // Mention the file which you want to send
            String filename = System.getProperty("user.dir")+"\\reports\\html-reports\\cucumber-html-reports\\overview-features.html";
            // Create data source and pass the filename
            DataSource source = new FileDataSource(filename);
            // set the handler
            messageBodyPart2.setDataHandler(new DataHandler(source));
            // set the file
            messageBodyPart2.setFileName(filename);
            // Create object of MimeMultipart class
            Multipart multipart = new MimeMultipart();
            // add body part 1
            multipart.addBodyPart(messageBodyPart2);
            // add body part 2
            multipart.addBodyPart(messageBodyPart1);
            // set the content
            message.setContent(multipart);
            // finally send the email
            Transport.send(message);
            System.out.println("=====Email Sent=====");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
