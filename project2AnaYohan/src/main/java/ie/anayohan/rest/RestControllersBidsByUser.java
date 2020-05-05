package ie.anayohan.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.anayohan.domain.Bid;
import ie.anayohan.domain.User;
import ie.anayohan.services.BidService;
import ie.anayohan.services.UserService;

@RestController // Returned data is sent to a HTTP response body, raw data is formatted as JSON by default
@RequestMapping("/api")
public class RestControllersBidsByUser {

	@Autowired
	UserService userService;
	
	@Autowired
	BidService bidService;
	
	@GetMapping("bidsbyuser/{userId}") //localhost:8080/api/bidsbyuser/{userId}
	public List<Bid> myRestBidsByUser(@PathVariable("userId") int userId){
		User user = userService.findUserByUserId(userId);
		return bidService.findBidByUser(user);
	}
}