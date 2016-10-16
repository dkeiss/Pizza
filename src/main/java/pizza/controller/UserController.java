package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.service.UserService;
import pizza.vo.UserVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Keiss on 22.09.2016.
 */
@Controller
@RequestMapping("rest/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserVO> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "users", method = RequestMethod.POST)
    public
    @ResponseBody
    List<UserVO> addUser(@RequestBody UserVO user) {
        return userService.addUser(user);
    }

    @RequestMapping(value = "users/{userId}", method = RequestMethod.PUT)
    public
    @ResponseBody
    void updateUser(@PathVariable("userId") Integer userId, @RequestBody UserVO user) {
        user.setId(userId);
        userService.updateUser(user);
    }

}
