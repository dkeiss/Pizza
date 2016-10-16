package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.controller.exception.NoUserFoundException;
import pizza.domain.User;
import pizza.service.UserService;
import pizza.vo.UserVO;

import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daniel Keiss on 22.09.2016.
 */
@Controller()
public class UserController {

    @Autowired
    private UserService userService;

    private List<UserVO> userMock = new ArrayList<>();

    public UserController(){
        UserVO user = new UserVO();
        user.setAdmin(false);
        user.setFirstname("Hans");
        user.setLastname("Maulwurf");
        user.setUsername("hans@maulwurf.de");
        user.setId(1l);
        user.setRabatt(BigDecimal.ONE);
        userMock.add(user);

        UserVO admin = new UserVO();
        admin.setAdmin(true);
        admin.setFirstname("Homer");
        admin.setLastname("Simson");
        user.setUsername("homer@simson.de");
        admin.setId(2l);
        admin.setRabatt(BigDecimal.ZERO);
        userMock.add(admin);
    }

    @RequestMapping(value = "rest/users", method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserVO> getUsers() {
        return userMock;
    }

    @RequestMapping(value = "rest/users", method = RequestMethod.POST)
    public
    @ResponseBody
    List<UserVO> addUser(@RequestBody UserVO user) {
        userMock.add(user);
        return userMock;
    }

    @RequestMapping(value = "rest/users/{userId}", method = RequestMethod.PUT)
    public
    @ResponseBody
    void updateUser(@PathVariable("userId") Long userId, @RequestBody UserVO user) {
        for (int i = 0; i < userMock.size(); i++) {
            UserVO userVO = userMock.get(i);
            if(userId.equals(userVO.getId())){
                userMock.remove(i);
                userMock.add(i, user);
                return;
            }
        }
        throw new NoUserFoundException();
    }

}
