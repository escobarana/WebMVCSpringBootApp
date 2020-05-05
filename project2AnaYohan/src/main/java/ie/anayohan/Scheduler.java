package ie.anayohan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ie.anayohan.domain.Job;
import ie.anayohan.services.JobService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Scheduler {
	@Autowired
	JobService jobService;
	
	// At 12:48:00 every day of every month of every year....
	@Scheduled(cron = "0 48 12 * * *")
	public void updateJobs() {
		update();	
	}
	
	private void update() {
		
		
		log.info("Updating jobs");
		
		List<Job> activeJobs = jobService.findAllActive();
		
		String s = "";
		for (Job j: activeJobs) {
			LocalDate today=LocalDate.now();
			long daysBetween = ChronoUnit.DAYS.between(j.getJobFirstpublished(), today);
			
			if(daysBetween >= 20) { // Close the job after 20 days
				jobService.upadateActive(false, j.getJobId());
			}
			if(j.isJobActive()) {
				s += j.getJobName() + " ";
			}
		}
			
		log.info("Active jobs: " + s);
		
		List<Job> inactiveJobs = jobService.findAllNotActive();
		
		String x = "";
		for (Job j: inactiveJobs)
			x += j.getJobName() + " ";
		log.info("Inactive jobs: " + x);
	}
	
	// --- SECURITY --- (this bean must be created at the end)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}