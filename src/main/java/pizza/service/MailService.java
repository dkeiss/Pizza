package pizza.service;

import pizza.domain.order.BulkOrder;

import java.util.List;

/**
 * Created by Daniel Keiss on 19.10.2016.
 */
public interface MailService {

    void sendBulkOrderInvitationToAll(BulkOrder bulkOrder, List<String> emailAddresses);

    void sendBulkOrderFinishedToSubscribers(String bulkOrderName, List<String> emailAddresses);

}
