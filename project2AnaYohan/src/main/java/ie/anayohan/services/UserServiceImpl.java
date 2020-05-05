package ie.anayohan.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ie.anayohan.dao.UserDao;
import ie.anayohan.domain.Role;
import ie.anayohan.domain.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;

	@Override
	public User save(String userName, String userLastName, String userEmail, String userPassword, String userPhone, Role role, boolean enabled) {
		if(userDao.existsByUserEmail(userEmail)) {
			return null;
		}
		User user = new User(userName,userLastName,userEmail,userPassword,userPhone, role, enabled);
		return userDao.save(user);
	}

	@Override
	public User findUserByemail(String userEmail) {
		if(userDao.existsByUserEmail(userEmail)) {
			return userDao.findByUserEmail(userEmail);
		}
		return null;
		
	}
	

	@Override
	public User save(User user) {
		if(userDao.existsByUserEmail(user.getUserEmail())) {
			return null;
		}
		return userDao.save(user);
	}

	@Override
	public User findUserByUserId(int userId) {
		if(userDao.existsByUserId(userId)) {
			return userDao.findByUserId(userId);
		}
		return null;
	}
}