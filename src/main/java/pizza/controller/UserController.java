package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pizza.domain.User;
import pizza.service.UserService;

import java.util.List;

/**
 * Created by Daniel Keiss on 22.09.2016.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public @ResponseBody List<User> getUsers(){
        return userService.getUsers();
    }

}
