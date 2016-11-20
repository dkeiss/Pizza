package pizza.service;

/**
 * Created by Daniel Keiss on 19.10.2016.
 */
public interface MailService {

    void sendBulkOrderInvitationToAll(String bulkOrderName);

    void sendBulkOrderFinishedToSubscribers(Integer bulkOrderId);

}
