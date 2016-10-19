package pizza.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.cglib.core.CollectionUtils;
import org.mockito.internal.util.MockUtil;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pizza.domain.User;
import pizza.repositories.UserRepository;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Daniel Keiss on 19.10.2016.
 */
public class SendMailImplTest {

    @InjectMocks
    private SendMail sendMail = new SendMailImpl();

    @Mock
    private UserRepository userRepository;

    @Before
    public void before() {
        initMocks(this);

        User user = new User(); // TODO mock after final implementation
        user.setUserName("daniel.keiss@gmail.com");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        Whitebox.setInternalState(sendMail, "smtpHost", "smtp.googlemail.com");
    }

    @Test
    public void sendInvitationToAll() throws Exception {
        sendMail.sendInvitationToAll();
    }

}