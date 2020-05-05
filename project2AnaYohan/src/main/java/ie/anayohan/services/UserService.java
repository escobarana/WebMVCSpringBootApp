package ie.anayohan.services;

import ie.anayohan.domain.Role;
import ie.anayohan.domain.User;

public interface UserService {
	User save(String userName, String userLastName, String userEmail, String userPassword, String userPhone, Role role, boolean enabled);
	User save(User user);
	User findUserByemail(String userEmail);
	User findUserByUserId(int userId);
}