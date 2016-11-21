package pizza.controller.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.service.UserService;
import pizza.service.exception.user.UserNotLoggedInException;
import pizza.vo.user.UserVO;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static pizza.controller.rest.ResponseUtil.getEmptyJsonSucessResponse;

/**
 * Created by Daniel Keiss on 22.09.2016.
 */
@RestController
@RequestMapping("rest/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<UserVO> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "current", method = RequestMethod.GET)
    public UserVO getCurrentUser(Principal principal) {
        if (principal == null) {
            throw new UserNotLoggedInException();
        }
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        return userService.getUserByName(authenticationToken.getName());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public UserVO createUser(@RequestBody UserVO user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public Map updateUser(@PathVariable("userId") Integer userId, @RequestBody UserVO user) {
        user.setUserId(userId);
        userService.updateUser(user);
        return getEmptyJsonSucessResponse();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public Map deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        return getEmptyJsonSucessResponse();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public UserVO getUser(@PathVariable("userId") Integer userId) {
        return userService.getUser(userId);
    }

}
