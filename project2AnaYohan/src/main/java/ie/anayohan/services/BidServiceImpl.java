package ie.anayohan.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.anayohan.dao.BidDao;
import ie.anayohan.domain.Bid;
import ie.anayohan.domain.Job;
import ie.anayohan.domain.User;

@Service
public class BidServiceImpl implements BidService{

	@Autowired
	BidDao bidDao;
	
	@Override
	public Bid save(float bidValue, Job job, User user) {
		if(bidDao.existsByUserAndJobAndBidValue(user, job, bidValue)) {
			return null;
		}
		Bid bid=new Bid(bidValue, job, user);
		return bidDao.save(bid);
	}

	@Override
	public Bid findBidById(int bidId) {
		if(bidDao.existsBybidId(bidId)) {
			return bidDao.findByBidId(bidId);
		}
		return null;
		
	}

	@Override
	public List<Bid> findBidByUser(User user) {
		return bidDao.findByUser(user);
	}

	@Override
	public List<Bid> findBidByJob(Job job) {
		return bidDao.findByJob(job);
	}

	@Override
	public Bid save(Bid bid) {
		if(bidDao.existsByUserAndJobAndBidValue(bid.getUser(), bid.getJob(), bid.getBidValue())) {
			return null;
		}

		return bidDao.save(bid);
	}

	@Override
	public List<Bid> findAll() {
		return bidDao.findAll();
	}

	@Override
	public Bid findBestBidByJob(Job job) {
		List<Bid> bids =  bidDao.findByJob(job);
		Bid bestbid = new Bid(1000000000, null, null);
		boolean change = false;
		for(Bid bid: bids) {
			if(bid.getBidValue()<bestbid.getBidValue()) {
				bestbid = bid;
				change = true;
			}
		}
		if(change) {
			return bestbid;
		}else {
			return null;
		}
	}
}