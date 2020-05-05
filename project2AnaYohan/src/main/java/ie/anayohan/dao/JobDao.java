package ie.anayohan.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ie.anayohan.domain.Job;
import ie.anayohan.domain.User;

public interface JobDao extends JpaRepository<Job, Integer>{
	
	Job findByJobId(int jobId);
	
	List<Job> findAll();
	
	boolean existsByJobName(String jobName);
	
	boolean existsByJobId(int id);
	
	List<Job> findByUser(User user);
	
	List<Job> findByjobActive(boolean jobActive);
	
	@Modifying
	@Transactional
	@Query("UPDATE Job j SET j.jobActive =:bool WHERE j.jobId =:jobId")
	void changeActive(@Param("bool") boolean bool, @Param("jobId") int jobId);
}