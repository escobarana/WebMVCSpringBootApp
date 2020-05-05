package ie.anayohan;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.scheduling.annotation.EnableScheduling;

import ie.anayohan.dao.RoleDao;
import ie.anayohan.dao.UserDao;
import ie.anayohan.domain.Job;
import ie.anayohan.domain.Role;
import ie.anayohan.domain.User;
import ie.anayohan.services.BidService;
import ie.anayohan.services.JobService;

@SpringBootApplication 
@EnableScheduling
public class WebMvcApplication implements CommandLineRunner{

	@Autowired
	JobService jobService;
	
	@Autowired
	BidService bidService;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	Scheduler scheduler;
	
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_API = "ROLE_API";
	
	public static void main(String[] args) {
		SpringApplication.run(WebMvcApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		// Creating the roles
		Role role1 = new Role("user1@cit.ie", ROLE_USER);
		Role role2 = new Role("user2@cit.ie", ROLE_USER);
		Role role3 = new Role("a.escobar-llamazares@mycit.ie", ROLE_ADMIN);
		Role role4 = new Role("yohan.couanon@mycit.ie", ROLE_ADMIN);
		Role api = new Role("apirole@springproject.ie", ROLE_API);
		
		// Saving the roles in the db
		roleDao.save(role1);
		roleDao.save(role2);
		roleDao.save(role3);
		roleDao.save(role4);
		roleDao.save(api);
		
		//Creating the users
		User user1 = new User("Greg", "White", "user1@cit.ie", passwordEncoder.encode("12345678"), "123400789", role1, true);
		User user2 = new User("Mary", "Potter", "user2@cit.ie", passwordEncoder.encode("12345678"), "123456789", role2, true);
		User user3 = new User("Ana", "Escobar", "a.escobar-llamazares@mycit.ie", passwordEncoder.encode("12345678"), "56900789", role3, true);
		User user4 = new User("Yohan", "Couanon", "yohan.couanon@mycit.ie", passwordEncoder.encode("12345678"), "222116789", role4, true);
		User user5 = new User("ApiPerson", "SpringProject", "apirole@springproject.ie", passwordEncoder.encode("12345678"), "222116710", api, true);
	
		// Saving the users in the db
		userDao.save(user1);
		userDao.save(user2);
		userDao.save(user3);
		userDao.save(user4);
		userDao.save(user5);
		
		// Creating jobs
		Job job1 = jobService.save("Babysitter", "I need someone to babysit my child of 4 years old on 14/12/2019 from 20:00 until 23:00. 10€/hour", user1);
		Job job2 = jobService.save("Painter needed", "The job consists on painting a wall in a school. For further information contact me.", user2);
		
		
		// Creating an inactive job to check it is added to the inactive list
		Job jobinactive3 = new Job("Secret job","Contact me", user3);
		jobinactive3.setJobFirstpublished(LocalDate.of(2019, 03, 16));
		jobService.save(jobinactive3);
		
		// Bidding for the jobs
		bidService.save(5,jobinactive3, user4);
		bidService.save(4,jobinactive3, user2);
		bidService.save(3, job1, user2);
		bidService.save(4, job2, user1);
		bidService.save(2, job1, user3);
		bidService.save(2, job2, user4);
		
		
		// Assign the inactive jobs to the inactive list, the update method will be called always when we run the application
		scheduler.updateJobs();
	}
}