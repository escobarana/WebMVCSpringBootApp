package ie.anayohan.services;

import java.util.List;

import ie.anayohan.domain.Job;
import ie.anayohan.domain.User;

public interface JobService {
	Job save(String jobName, String jobDescription, User user);
	Job save(Job job);
	List<Job> findAllJobs();
	Job findJobById(int jobId);
	List<Job> findJobsByUser(User user);
	List<Job> findAllActive();
	List<Job> findAllNotActive();
	void upadateActive(boolean bool, int jobId);
}