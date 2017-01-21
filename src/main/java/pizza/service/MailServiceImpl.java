package pizza.service;

import org.apache.commons.mail.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pizza.domain.order.BulkOrder;
import pizza.domain.user.User;
import pizza.repositories.UserRepository;
import pizza.vo.order.BulkOrderVO;
import pizza.vo.order.UserOrderDetailsVO;

import java.text.SimpleDateFormat;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by Daniel Keiss on 19.10.2016.
 */
@Service
public class MailServiceImpl implements MailService {

    @Value("${smtp.host}")
    private String smtpHost;

    @Value("${email.link.host}")
    private String emailLinkHost;

    private final SimpleDateFormat timeOfDay = new SimpleDateFormat("HH:mm");

    @Override
    public void sendBulkOrderInvitationToAll(BulkOrder bulkOrder, List<String> emailAddresses) {
        new Thread(() -> {
            try {
                String bulkOrderName = bulkOrder.getName();
                String activeUntil = timeOfDay.format(bulkOrder.getActiveUntil());

                MultiPartEmail email = new MultiPartEmail();
                email.setHostName(smtpHost);
                email.setSmtpPort(465);
                email.setAuthenticator(new DefaultAuthenticator("whbpizza", "whb@pizza"));
                email.setSSLOnConnect(true);
                email.setFrom("whbpizza@gmail.com");
                email.setSubject(format("Die Sammelbestellung \"%s\" ist bis \"%s\" Uhr eröffnet!", bulkOrderName, activeUntil));
                email.setMsg(format("Bestellungen können nun bis %s Uhr aufgenommen werden!\n\nLink: %s.\n\nEine Kurzanleitung ist hier zu finden: %s\n\nGuten Appetit!", activeUntil, emailLinkHost, emailLinkHost + "/rest/admin/productcatalog/quick-start-guide"));

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
