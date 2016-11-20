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

/**
 * Created by Daniel Keiss on 19.10.2016.
 */
@Service
public class MailServiceImpl implements MailService {

    @Value("${smtp.host}")
    private String smtpHost;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserOrderService userOrderService;

    @Autowired
    private BulkOrderService bulkOrderService;

    @Override
    public void sendBulkOrderInvitationToAll(String bulkOrderName) {
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

                for (User user : userRepository.findAll()) {
                    email.addTo(user.getUserName());
                }

                email.send();
            } catch (EmailException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void sendBulkOrderFinishedToSubscribers(Integer bulkOrderId) {
        new Thread(() -> {
            BulkOrderVO bulkOrderVO = bulkOrderService.getBulkOrderById(bulkOrderId);
            try {
                Email email = new SimpleEmail();
                email.setHostName(smtpHost);
                email.setSmtpPort(465);
                email.setAuthenticator(new DefaultAuthenticator("whbpizza", "whb@pizza"));
                email.setSSLOnConnect(true);
                email.setFrom("whbpizza@gmail.com");
                email.setSubject("Die Sammelbestellung \"" + bulkOrderVO.getName() + "\" ist abgeschlossen!");
                email.setMsg("Die Bestellungen können nun abgeholt werden.");

                for (UserOrderDetailsVO userOrderDetails : userOrderService.getCurrentUserOrders()) {
                    email.addTo(userOrderDetails.getUserName());
                }

                email.send();
            } catch (EmailException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
