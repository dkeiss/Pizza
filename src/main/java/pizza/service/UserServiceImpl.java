package pizza.service;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pizza.domain.user.PasswordType;
import pizza.domain.user.User;
import pizza.repositories.UserRepository;
import pizza.service.exception.NotFoundException;
import pizza.service.exception.user.UsernameAlreadyUsedException;
import pizza.vo.admin.InitialAdminVO;
import pizza.vo.user.UserVO;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import static pizza.service.common.ObjectMapperUtil.*;

/**
 * Created by Daniel Keiss on 11.09.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean usernameExist(String username) {
        return findUserByUsername(username) != null;
    }

    @Override
    public boolean isUsernameAndPasswordValid(String username, String password) {
        User user = findUserByUsername(username);
        return user != null && checkPassword(password, user);
    }

    private boolean checkPassword(String password, User user) {
        if (!StringUtils.hasText(user.getPassword()) || !StringUtils.hasText(password)) {
            return false;
        } else if (PasswordType.PLAIN.equals(user.getPasswordType())) {
            return user.getPassword().equals(password);
        } else if (PasswordType.ENCRYPTED.equals(user.getPasswordType())) {
            String hash = sha3hash(password);
            return user.getPassword().equals(hash);
        }
        throw new IllegalArgumentException("No Password Type set!");
    }

    @Override
    public boolean isAdmin(String username) {
        User user = findUserByUsername(username);
        return user.isAdmin();
    }

    @Override
    public List<UserVO> getUsers() {
        return copyListFromBusinessObject(userRepository.findAll(), UserVO.class);
    }

    @Override
    public UserVO createUser(UserVO userVO) {
        if (usernameExist(userVO.getUserName())) {
            throw new UsernameAlreadyUsedException();
        }
        userVO.setUserId(null);
        User user = copyFromValueObject(userVO, new User());
        user.setCreationDate(new Date());
        userRepository.save(user);
        return copyFromBusinessObject(user, new UserVO());
    }

    @Override
    public void updateUser(UserVO userVO) {
        User user = getUserById(userVO.getUserId());
        copyFromValueObject(userVO, user);
        user.setModificationDate(new Date());
        userRepository.save(user);
    }

    @Override
    public UserVO getUser(Integer userId) {
        User user = getUserById(userId);
        return copyFromBusinessObject(user, new UserVO());
    }

    private User getUserById(Integer userId) {
        User user = findUser(userId);
        if (user == null) {
            throw new NotFoundException();
        }
        return user;
    }

    @Override
    public boolean isInitialAdminPassword(String username) {
        User user = findUserByUsername(username);
        return user.isAdmin() && user.getPassword() == null;
    }

    @Override
    public void setInitialAdminPassword(String username, String password) {
        User user = findUserByUsername(username);

        String hash = sha3hash(password);

        user.setPassword(hash);
        user.setPasswordType(PasswordType.ENCRYPTED);
        userRepository.save(user);
    }

    public String sha3hash(String password) {
        try {
            SHA3.DigestSHA3 md = new SHA3.DigestSHA3(256);
            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return Hex.toHexString(digest);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(Integer userId) {
        try {
            userRepository.delete(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public boolean userExists(Integer userId) {
        return userRepository.exists(userId);
    }

    @Override
    public User findUser(Integer userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public boolean isInitialAdminValid(InitialAdminVO initialAdmin) {
        if (!StringUtils.hasText(initialAdmin.getFirstName())) {
            return false;
        }
        if (!StringUtils.hasText(initialAdmin.getLastName())) {
            return false;
        }
        if (!StringUtils.hasText(initialAdmin.getPassword())) {
            return false;
        }
        if (!StringUtils.hasText(initialAdmin.getPasswordCheck())) {
            return false;
        }
        return StringUtils.hasText(initialAdmin.getUsername()) && initialAdmin.getPassword().equals(initialAdmin.getPasswordCheck());
    }

}
