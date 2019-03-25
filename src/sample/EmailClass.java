package sample;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;

public class EmailClass {

    public static String userMail;
    public static String userName;
    public static String productDesc;
    public static String productCode;
    public static String productSize;
    public static int productQty;
    public static double cost;
    public static double total;


    public static String invoiceDetails() {
        String data = "\t\t\t\t\t\t\t     Jeff’s Fishing Shack\n" +
                "\n" +
                "\t\t\t\t\t\t\t\tTax Invoice\n" +
                "\n" +
                "\n" +
                "Jeff’s Fishing Shack\n" +
                "Trading as: Octopus Pty Ltd\n" +
                "Shop 4, Hillarys Boat Harbour\n" +
                "Hillarys, WA, 6025\n" +
                "T:08 9402 2222\n" +
                "E:Sales@JFS.com.au\n" +
                "\n" +
                "\n" +
                "Receipt#: 10412\t\t\t\t\t\t\t\t\t\t\t\t\t\tDate: 04/08/2019\n\n" +
                "Customer: " + userName + "\n" +
                "1, Shark Loop\n" +
                "Mindarien\n" +
                "W.A. 6027\n\n" +
                "Customer email: " + userMail + "\n\n" +
                "\tPurchases:\n" +
                "\t\t\t\t No.\tDescription\tCode\tSize\tCost\tQty\tAmount\n" +
                "\t\t\t\t  1\t" + productDesc + "\t" + productCode + "\t" + productSize + "\t" + cost + "\t" + productQty + "\t" + total + "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\tTotal Paid: " + total + "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\tThank you for your business.\n" +
                "\t\t\t\t\t\t   Jeff’s - where the real fishermen shop.\n";

        return data;
    }

    public static void sendEmail(String subject, String messageText) {

        try {
            String host = "smtp.gmail.com";
            String user = "merianlesster27.ml@gmail.com";
            String pass = "";// Admin Password Here
            String to = ""; // customer's mail
            String from = "merianlesster27.ml@gmail.com"; // Sender's mail (Admin)

            boolean sessionDebug = false;
            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(messageText);
            msg.setFileName(subject);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("message send successfully");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}

