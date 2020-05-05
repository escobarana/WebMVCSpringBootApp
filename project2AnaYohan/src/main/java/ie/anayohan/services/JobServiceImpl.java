package ie.anayohan.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.anayohan.dao.JobDao;
import ie.anayohan.domain.Job;
import ie.anayohan.domain.User;

@Service
public class JobServiceImpl implements JobService{
	@Autowired 
	JobDao jobDao;
	
	@Override
	public Job save(String jobName, String jobDescription, User user) {
		if(jobDao.existsByJobName(jobName)) {
			return null;
		}
		Job job=new Job(jobName,jobDescription,user);
		return jobDao.save(job);
	}

	@Override
	public List<Job> findAllJobs() {
		return jobDao.findAll();
	}

	@Override
	public Job findJobById(int jobId) {
		if(!jobDao.existsByJobId(jobId)) {
			return null;
		}
		return jobDao.findByJobId(jobId);
	}

	@Override
	public List<Job> findJobsByUser(User user) {
		return jobDao.findByUser(user);
	}

	@Override
	public Job save(Job job) {
		if(jobDao.existsByJobName(job.getJobName())) {
			return null;
		}

		return jobDao.save(job);
	}

	@Override
	public List<Job> findAllActive() {
		return jobDao.findByjobActive(true);
	}

	@Override
	public List<Job> findAllNotActive() {
		return jobDao.findByjobActive(false);
	}

	@Override
	public void upadateActive(boolean bool, int jobId) {
		jobDao.changeActive(bool, jobId);
	}
}
