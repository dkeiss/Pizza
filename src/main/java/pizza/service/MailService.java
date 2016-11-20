package pizza.service;

import java.util.List;

/**
 * Created by Daniel Keiss on 19.10.2016.
 */
public interface MailService {

    void sendBulkOrderInvitationToAll(String bulkOrderName, List<String> emailAddresses);

    void sendBulkOrderFinishedToSubscribers(String bulkOrderName, List<String> emailAddresses);

}
