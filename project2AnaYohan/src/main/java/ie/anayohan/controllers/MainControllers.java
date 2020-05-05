package ie.anayohan.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ie.anayohan.dao.RoleDao;
import ie.anayohan.domain.Bid;
import ie.anayohan.domain.Job;
import ie.anayohan.domain.Role;
import ie.anayohan.domain.User;
import ie.anayohan.form.BidForm;
import ie.anayohan.form.JobForm;
import ie.anayohan.form.UserForm;
import ie.anayohan.services.BidService;
import ie.anayohan.services.JobService;
import ie.anayohan.services.UserService;

@Controller // Tells Spring this class contains controllers (handlers)
public class MainControllers {
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	JobService jobService;
	@Autowired
	BidService bidService;
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	@GetMapping(value="/") // Tells Spring that a method handles a Get request. "localhost:8080/"Get request
	public String handleIndexRequest(Model model) {
		return "index"; // Resolved to index.html because we are using Thymeleaf
	}
	
	@GetMapping(value="/user")
	public String welcomePage(Principal p, Model model) {
		User user = userService.findUserByemail(p.getName());
		model.addAttribute("user", user);
		List<Bid> bids = bidService.findBidByUser(user);
		model.addAttribute("bids", bids);

		return "userpage";
	}
	
	@GetMapping(value="/job/{id}")
	public String showDescriptionJob(@PathVariable("id") int id, Model model) {
		Job job = jobService.findJobById(id);
		Bid bestbid = bidService.findBestBidByJob(job);
		boolean nobid = false;
		if(bestbid==null) {
			nobid = true;
		}
		model.addAttribute("nobid", nobid);
		model.addAttribute("job",job);
		model.addAttribute("bestbid",bestbid);
		return "job";
	}
	
	@GetMapping(value="/bids")
	public String showListBidsGet(Model model) {
		List<Bid> bids = bidService.findAll();
		model.addAttribute("bids",bids);
		return "list_bids";
	}
	
	@PostMapping(value="/bids")
	public String showListBidsPost(Model model) {
		List<Bid> bids = bidService.findAll();
		model.addAttribute("bids",bids);
		return "list_bids";
	}
	
	@GetMapping(value="/jobs")
	public String showListJobsGet(Model model) {
		List<Job> jobs = jobService.findAllJobs();
		model.addAttribute("jobs",jobs);
		return "list_jobs";
	}
	
	@PostMapping(value="/jobs")
	public String showListJobsPost(Model model) {
		List<Job> jobs = jobService.findAllJobs();
		model.addAttribute("jobs",jobs);
		return "list_jobs";
	}
	
	@GetMapping(value="/activejobs")
	public String showListActiveJobsGet(Model model) {
		List<Job> jobs = jobService.findAllActive();
		model.addAttribute("jobs",jobs);
		return "list_activejobs";
	}
	
	@PostMapping(value="/activejobs")
	public String showListActiveJobsPost(Model model) {
		List<Job> jobs = jobService.findAllActive();
		model.addAttribute("jobs",jobs);
		return "list_activejobs";
	}
	
	@GetMapping(value="/inactivejobs")
	public String showListInactiveJobsGet(Model model) {
		List<Job> jobs = jobService.findAllNotActive();
		model.addAttribute("jobs",jobs);
		return "list_inactivejobs";
	}
	
	@PostMapping(value="/inactivejobs")
	public String showListInactiveJobsPost(Model model) {
		List<Job> jobs = jobService.findAllNotActive();
		model.addAttribute("jobs",jobs);
		return "list_inactivejobs";
	}
	
	@GetMapping("/newuser")
	public String addNewUser(Model model){
		model.addAttribute("userForm", new UserForm());
		return "newuser";
	}
	
	@PostMapping("/newuser")
	public String addNewUser(Model model, @Valid UserForm userForm, BindingResult binding, RedirectAttributes redirectAttributes) {
		Role role = new Role(userForm.getUserEmail(), "ROLE_USER");
		roleDao.save(role);
		
		User user = new User(userForm.getUserName(), userForm.getUserLastname(), userForm.getUserEmail(), passwordEncoder.encode(userForm.getUserPassword()), userForm.getUserPhone(),role, true);
		
		
		if(userService.save(user)==null) {
			redirectAttributes.addFlashAttribute("duplicate", true);
			return "redirect:/newuser";
		}
		
		return "redirect:/user/";
	}
	
	@GetMapping("/user/newbid")
	public String addNewBid(Principal p, Model model){
		User user = userService.findUserByemail(p.getName());
		List<Job> jobs = jobService.findAllActive();
		model.addAttribute("user", user);
		model.addAttribute("jobs", jobs);
		model.addAttribute("bidForm", new BidForm());
		return "newbid";
	}
	
	@PostMapping("/user/newbid")
	public String addNewBid(Principal p, Model model, @Valid BidForm bidForm, BindingResult binding, RedirectAttributes redirectAttributes) {
		User user = userService.findUserByemail(p.getName());
		Job job = jobService.findJobById(bidForm.getJobId());
		Bid bestbid = bidService.findBestBidByJob(job);
		if(user.equals(job.getUser())) {
			redirectAttributes.addFlashAttribute("ownjob", true);
			return "redirect:/user/newbid";
		}
		if(bidForm.getBidValue()<=0) {
			redirectAttributes.addFlashAttribute("valuezero", true);
			return "redirect:/user/newbid";
		}
		if(bestbid!=null) {
			if(bidForm.getBidValue()>=bestbid.getBidValue()) {
				redirectAttributes.addFlashAttribute("lessvalue", true);
				return "redirect:/user/newbid";
			}
		}
		Bid bid = new Bid(bidForm.getBidValue(), job, user);
		if(bidService.save(bid)==null) {
			redirectAttributes.addFlashAttribute("duplicate", true);
			return "redirect:/user/newbid";
		}
		return "redirect:/user/";
	}
	
	@GetMapping("/user/newjob")
	public String addNewJob(Model model, Principal p){
		User user = userService.findUserByemail(p.getName());
		model.addAttribute("user", user);
		model.addAttribute("jobForm", new JobForm());
		return "newjob";
	}
	
	@PostMapping("/user/newjob")
	public String addNewJob(Principal p, Model model, @Valid JobForm jobForm, BindingResult binding, RedirectAttributes redirectAttributes) {
		User user = userService.findUserByemail(p.getName());
		Job job = new Job(jobForm.getJobName(), jobForm.getJobDescription(), user);
		if(jobService.save(job)==null){
			redirectAttributes.addFlashAttribute("duplicate", true);
			return "redirect:/user/newjob";
		}
		return "redirect:/user/";
	}
}