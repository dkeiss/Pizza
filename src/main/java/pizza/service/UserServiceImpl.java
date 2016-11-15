package pizza.service;

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
		if (user != null) {
			return checkPassword(password, user);
		}
		return false;
	}

	private boolean checkPassword(String password, User user) {
		if (user.getPassword() == null || password == null) {
			return false;
		} else if (PasswordType.PLAIN.equals(user.getPasswordType())) {
			return user.getPassword().equals(password);
		} else if (PasswordType.ENCRYPTED.equals(user.getPasswordType())) {
			throw new IllegalArgumentException("Not implemented yet!");
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
		user.setPassword(password);
		user.setPasswordType(PasswordType.PLAIN);
		userRepository.save(user);
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
		if (!StringUtils.hasText(initialAdmin.getUsername())) {
			return false;
		}
		return initialAdmin.getPassword().equals(initialAdmin.getPasswordCheck());
	}

}
