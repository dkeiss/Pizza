package pizza.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pizza.domain.User;
import pizza.repositories.UserRepository;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Daniel Keiss on 19.10.2016.
 */
@Service
public class SendMailImpl implements SendMail {

    @Value("${smtp.host}")
    private String smtpHost;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendInvitationToAll() throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName(smtpHost);
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("whbpizza", "whb@pizza"));
        email.setSSLOnConnect(true);
        email.setFrom("whbpizza@gmail.com");
        email.setSubject("Das Bestellsystem ist eröffnet!");
        email.setMsg("Bestellungen können nun aufgenommen werden :-)");

        for (User user : userRepository.findAll()) {
            email.addTo(user.getUserName());
        }

        email.send();
    }
}
