package pizza.service;

import org.apache.commons.mail.EmailException;

/**
 * Created by Daniel Keiss on 19.10.2016.
 */
public interface SendMail {

    void sendInvitationToAll() throws EmailException;

}
