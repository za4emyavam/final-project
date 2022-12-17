package com.myfp.fp.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailReport {
    //private static final Logger LOG4J = LogManager.getLogger(MailReport.class);

    public static void registrationMail(String address, String name, String pass) {
        String text = "Dear " + name + ","
                + "\n\nYour account was successful registered. \n\n" +
                "Here your login details: \nLogin: " + address + "\nPassword: " + pass;

        sendMail(address, text);
    }

    public static void replenishMail(String address, String name, String amount) {
        String text = "Dear " + name + ","
                + "\n\nYou have successfully replenished your balance in the amount of " + amount + " UAH.";

        sendMail(address, text);
    }

    public static void requestMail(String address, String name, String tariffName) {
        String text = "Dear " + name + ","
                + "\n\nYou have applied for connection of the \"" + tariffName + "\" tariff. " +
                "Your application will be reviewed as soon as possible. The verdict will be sent to your email. " +
                "\nThank you for choosing our company!";
        sendMail(address, text);
    }

    private static void sendMail(String address, String text) {
        final String username = "shuhnikita@gmail.com";
        final String password = "llvzyevtmcuukyue";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("shuhnikita@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(address)
            );
            message.setSubject("Internet Provider \"Fastest\"");
            message.setText(text);

            Transport.send(message);

        } catch (MessagingException e) {
            //LOG4J.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
