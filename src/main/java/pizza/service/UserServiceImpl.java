package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.user.PasswordType;
import pizza.domain.user.User;
import pizza.repositories.UserRepository;
import pizza.service.exception.NotFoundException;
import pizza.service.exception.UsernameAlreadyUsedException;
import pizza.service.common.ObjectMapperService;
import pizza.vo.user.UserVO;

import java.util.Date;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;


/**
 * Created by Daniel Keiss on 11.09.2016.
 */
@Service
public class UserServiceImpl implements UserService, ObjectMapperService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean usernameExist(String username) {
        return getUserByUsername(username) != null;
    }

    @Override
    public boolean isUsernameAndPasswordValid(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null) {
            return checkPassword(password, user);
        }
        return false;
    }

    private boolean checkPassword(String password, User user) {
        if (PasswordType.PLAIN.equals(user.getPasswordType())) {
            return user.getPassword().equals(password);
        } else if (PasswordType.ENCRYPTED.equals(user.getPasswordType())) {
            throw new IllegalArgumentException("Not implemented yet!");
        }
        throw new IllegalArgumentException("No Password Type set!");
    }

    @Override
    public boolean isAdmin(String username) {
        User user = getUserByUsername(username);
        return user.isAdmin();
    }

    @Override
    public List<UserVO> getUsers() {
        return copyListFromBusinessObject(userRepository.findAll(), UserVO.class);
    }

    @Override
    public List<UserVO> createUser(UserVO userVO) {
        if (usernameExist(userVO.getUserName())) {
            throw new UsernameAlreadyUsedException();
        }
        userVO.setUserId(null);
        User user = copyFromValueObject(userVO, new User());
        user.setCreationDate(new Date());
        userRepository.save(user);
        return getUsers();
    }

    @Override
    public void updateUser(UserVO userVO) {
        User user = userRepository.findOne(userVO.getUserId());
        if (user == null) {
            throw new NotFoundException();
        }
        copyFromValueObject(userVO, user);
        user.setModificationDate(new Date());
        userRepository.save(user);
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

}
