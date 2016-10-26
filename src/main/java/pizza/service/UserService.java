package pizza.service;

import pizza.vo.user.UserVO;

import java.util.List;

/**
 * Created by Daniel Keiss on 11.09.2016.
 */
public interface UserService {

    boolean usernameExist(String username);

    boolean isUsernameAndPasswordValid(String username, String password);

    boolean isAdmin(String alias);

    List<UserVO> getUsers();

    List<UserVO> addUser(UserVO user);

    void updateUser(UserVO user);

}
