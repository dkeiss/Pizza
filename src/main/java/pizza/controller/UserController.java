package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.service.UserService;
import pizza.vo.user.UserVO;

import java.util.List;

/**
 * Created by Daniel Keiss on 22.09.2016.
 */
@Controller
@RequestMapping("rest/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserVO> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    List<UserVO> createUser(@RequestBody UserVO user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public
    @ResponseBody
    void updateUser(@PathVariable("userId") Integer userId, @RequestBody UserVO user) {
        user.setId(userId);
        userService.updateUser(user);
    }

}
