package pizza.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pizza.domain.user.User;
import pizza.repositories.UserRepository;
import pizza.vo.order.BulkOrderVO;
import pizza.vo.order.UserOrderDetailsVO;

import java.util.List;

/**
 * Created by Daniel Keiss on 19.10.2016.
 */
@Service
public class MailServiceImpl implements MailService {

    @Value("${smtp.host}")
    private String smtpHost;

    @Override
    public void sendBulkOrderInvitationToAll(String bulkOrderName, List<String> emailAddresses) {
        new Thread(() -> {
            try {
                Email email = new SimpleEmail();
                email.setHostName(smtpHost);
                email.setSmtpPort(465);
                email.setAuthenticator(new DefaultAuthenticator("whbpizza", "whb@pizza"));
                email.setSSLOnConnect(true);
                email.setFrom("whbpizza@gmail.com");
                email.setSubject("Die Sammelbestellung \"" + bulkOrderName + "\" ist eröffnet!");
                email.setMsg("Bestellungen können nun aufgenommen werden!");

                for (String emailAdress : emailAddresses) {
                    email.addTo(emailAdress);
                }

                email.send();
            } catch (EmailException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void sendBulkOrderFinishedToSubscribers(String bulkOrderName, List<String> emailAddresses) {
        new Thread(() -> {
            try {
                Email email = new SimpleEmail();
                email.setHostName(smtpHost);
                email.setSmtpPort(465);
                email.setAuthenticator(new DefaultAuthenticator("whbpizza", "whb@pizza"));
                email.setSSLOnConnect(true);
                email.setFrom("whbpizza@gmail.com");
                email.setSubject("Die Sammelbestellung \"" + bulkOrderName + "\" ist abgeschlossen!");
                email.setMsg("Die Bestellungen können nun abgeholt werden.");

                for (String emailAdress : emailAddresses) {
                    email.addTo(emailAdress);
                }

                email.send();
            } catch (EmailException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
