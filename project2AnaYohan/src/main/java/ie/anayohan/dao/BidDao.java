package ie.anayohan.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.anayohan.domain.Bid;
import ie.anayohan.domain.Job;
import ie.anayohan.domain.User;

public interface BidDao extends JpaRepository<Bid, Integer>{
	
	boolean existsBybidId(int bidId);
	
	boolean existsByUserAndJobAndBidValue(User user, Job job, float bidValue);
	
	Bid findByBidId(int bidId);
	
	List<Bid> findByUser(User user);
	
	List<Bid> findByJob(Job job);
	
	List<Bid> findAll();
}