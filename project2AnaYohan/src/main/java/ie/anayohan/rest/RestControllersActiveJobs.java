package ie.anayohan.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.anayohan.domain.Job;
import ie.anayohan.services.JobService;

@RestController // Returned data is sent to a HTTP response body, raw data is formatted as JSON by default
@RequestMapping("/api")
public class RestControllersActiveJobs {

	@Autowired
	JobService jobService;
	
	@GetMapping("activejobs") //localhost:8080/api/activejobs
	public List<Job> myRestActiveJobs(){
		return jobService.findAllActive();
	}
}
