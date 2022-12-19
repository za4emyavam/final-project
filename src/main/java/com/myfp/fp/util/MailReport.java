package com.myfp.fp.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * The class responsible for sending various letters to clients
 */
public class MailReport {

    /**
     * After calling the method, the text of the letter is formed and the {@link #sendMail(String, String)}
     * method is called, which sends it to the specified address
     *
     * @param address recipient's email
     * @param name    recipient's name
     * @param pass    recipient's password
     */
    public static void registrationMail(String address, String name, String pass) {
        String text = "Dear " + name + ","
                + "\n\nYour account was successful registered. \n\n" +
                "Here your login details: \nLogin: " + address + "\nPassword: " + pass;

        sendMail(address, text);
    }

    /**
     * After calling the method, the text of the letter is formed and the {@link #sendMail(String, String)}
     * method is called, which sends it to the specified address
     *
     * @param address recipient's email
     * @param name    recipient's name
     * @param amount  replenished amount
     */
    public static void replenishMail(String address, String name, String amount) {
        String text = "Dear " + name + ","
                + "\n\nYou have successfully replenished your balance in the amount of " + amount + " UAH.";

        sendMail(address, text);
    }

    /**
     * After calling the method, the text of the letter is formed and the {@link #sendMail(String, String)}
     * method is called, which sends it to the specified address
     *
     * @param address recipient's email
     * @param name recipient's name
     * @param tariffName name of the tariff which is in the request
     */
    public static void requestMail(String address, String name, String tariffName) {
        String text = "Dear " + name + ","
                + "\n\nYou have applied for connection of the \"" + tariffName + "\" tariff. " +
                "Your application will be reviewed as soon as possible. The verdict will be sent to your email. " +
                "\nThank you for choosing our company!";
        sendMail(address, text);
    }

    /**
     * Sends an email to an <b>address</b> with <b>text</b> content using google smtp
     *
     * @param address recipient's email
     * @param text    text of the letter
     */
    private static void sendMail(String address, String text) {
        final String username = "shuhnikita@gmail.com";
        final String password = "llvzyevtmcuukyue";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

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
            e.printStackTrace();
        }
    }
}
