package pizza.controller.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.service.UserService;
import pizza.vo.user.UserVO;

import java.util.List;
import java.util.Map;

import static pizza.controller.rest.ResponseUtil.getEmptyJsonSucessResponse;

/**
 * Created by Daniel Keiss on 22.09.2016.
 */
@Controller
@RequestMapping("rest/user")
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
    UserVO createUser(@RequestBody UserVO user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Map updateUser(@PathVariable("userId") Integer userId, @RequestBody UserVO user) {
        user.setUserId(userId);
        userService.updateUser(user);
        return getEmptyJsonSucessResponse(true);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    Map deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        return getEmptyJsonSucessResponse(true);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public
    @ResponseBody
    UserVO getUser(@PathVariable("userId") Integer userId) {
        return userService.getUser(userId);
    }

}
