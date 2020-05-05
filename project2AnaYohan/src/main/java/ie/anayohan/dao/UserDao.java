package ie.anayohan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.anayohan.domain.User;

public interface UserDao extends JpaRepository<User, Integer>{ // <class from which the JpaRepository is created (User), type of its ID (int)>
	// This is auto implemented by Spring into a bean called userDao which has many CRUD operations.
	// This is not annotated as a bean because interfaces cannot be annotated as beans. Instead the generated implementation in a bean.
	User findByUserEmail(String userEmail);
	
	User findByUserId(int userId);
	
	boolean existsByUserEmail(String userEmail);
	
	boolean existsByUserId(int userId);
}