/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.mailer;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Nicolò Paoletti
 */
public class MailSender {
    String to;
    String ObjectMessage;
    String text;
    
    public MailSender(String to,String ObjectMessage,String text){
        this.to = to;
        this.ObjectMessage = ObjectMessage;
        this.text = text;
    }   
    
    public void sendMail(){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("paolettinic@gmail.com","");//mail e password
                }
            });

            try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("celticwarrior94@live.com")); //mail from
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(this.to));
            message.setSubject(this.ObjectMessage);
            message.setText(this.text);

        Transport.send(message);
            System.out.println("Email inviata con successo");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
}
