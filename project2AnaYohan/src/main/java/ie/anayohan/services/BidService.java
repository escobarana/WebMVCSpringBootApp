package ie.anayohan.services;

import java.util.List;

import ie.anayohan.domain.Bid;
import ie.anayohan.domain.Job;
import ie.anayohan.domain.User;

public interface BidService {
	Bid save(float bidValue, Job job, User user);
	Bid save(Bid bid);
	Bid findBidById(int bidId);
	List<Bid> findBidByUser(User user);
	List<Bid> findBidByJob(Job job);
	List<Bid> findAll();
	Bid findBestBidByJob(Job job);
}
