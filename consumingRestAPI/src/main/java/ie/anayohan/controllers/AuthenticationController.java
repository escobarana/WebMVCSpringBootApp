package ie.anayohan.controllers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import ie.anayohan.jsondata.Bid;
import ie.anayohan.jsondata.Job;

@Controller
public class AuthenticationController {
	
	public static final String USERNAME = "apirole@springproject.ie";
	public static final String PASSWORD = "12345678";
	
	@GetMapping("/showactivejobs")
	public String showActiveJobs(Model model) {
		
		try {
			
			RestTemplate restTemplate = new RestTemplate();
			String URL = "http://localhost:8080/api/activejobs";
			HttpHeaders headers = createHeaders(USERNAME, PASSWORD);
			
			ResponseEntity<List<Job>> responseEntity = restTemplate.exchange
					(
							URL, 
							HttpMethod.GET,
							new HttpEntity<>(headers),
							new ParameterizedTypeReference<List<Job>>() {}
					);
			List<Job> activejobs = responseEntity.getBody();
			model.addAttribute("activejobs", activejobs);
			return "activejobs";
			
		}catch(Exception e){
			System.err.println("Exception...{ " + e.getMessage() + " }");
			return "notfound";
		}
	}
	
	@GetMapping("showbids/{userId}")
	public String showBidsByUser(Model model, @PathVariable(name="userId") int userId) {
		
		try {
			
			RestTemplate restTemplate = new RestTemplate();
			String URL = "http://localhost:8080/api/bidsbyuser/"+userId;
			HttpHeaders headers = createHeaders(USERNAME, PASSWORD);
			
			ResponseEntity<List<Bid>> responseEntity = restTemplate.exchange
					(
							URL, 
							HttpMethod.GET,
							new HttpEntity<>(headers),
							new ParameterizedTypeReference<List<Bid>>() {}
					);
			
			List<Bid> bids = responseEntity.getBody();
			model.addAttribute("bids", bids);
			return "bids";
			
		}catch(Exception e){
			System.err.println("Exception...{ " + e.getMessage() + " }");
			return "notfound";
		}
	}
	
	

	HttpHeaders createHeaders(final String username, final String password) {

		return new HttpHeaders() {
			private static final long serialVersionUID = 1L;

		{
			String auth = username + ":" + password;
			byte[] encodeStringIntoBytes = auth.getBytes(StandardCharsets.UTF_8);
			byte[] encodedAuth = Base64.encodeBase64(encodeStringIntoBytes);
			String authHeader = "Basic " + new String( encodedAuth );
			//Logger.info("INFO ... {}" + authHeader);
			set(HttpHeaders.AUTHORIZATION, authHeader);
		}};
	}
}
