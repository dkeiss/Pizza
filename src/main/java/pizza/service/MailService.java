package pizza.service;

import org.apache.commons.mail.EmailException;

/**
 * Created by Daniel Keiss on 19.10.2016.
 */
public interface MailService {

    void sendBulkOrderInvitationToAll(String bulkOrderName) throws EmailException;

}
