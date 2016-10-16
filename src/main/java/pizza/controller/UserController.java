package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pizza.domain.User;
import pizza.service.UserService;
import pizza.vo.UserVO;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daniel Keiss on 22.09.2016.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "rest/users", method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserVO> getUsers() {
        UserVO user = new UserVO();
        user.setAdmin(false);
        user.setFirstname("Hans");
        user.setLastname("Maulwurf");
        user.setId(1l);
        user.setRabatt(BigDecimal.ONE);

        UserVO admin = new UserVO();
        admin.setAdmin(true);
        admin.setFirstname("Homer");
        admin.setLastname("Simson");
        admin.setId(2l);
        admin.setRabatt(BigDecimal.ZERO);

        return Arrays.asList(user, admin);
    }

}
