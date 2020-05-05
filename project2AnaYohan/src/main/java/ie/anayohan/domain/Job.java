package ie.anayohan.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // Tells Spring that User is a JPA entity which will be mapped to a table in the database called user. Maps to a table called user
@Getter
@Setter
@NoArgsConstructor
public class Job { // JOB IS CLOSED AFTER 20 DAYS - USE SCHEDULER

	@Id // Marks this as the primary key
	@GeneratedValue // Id's will be generated automatically
	private int jobId; // Maps to a field called job_id
	
	@Column(nullable=false) // For extra info
	private String jobName; // Maps to a field called job_name
	@Column(nullable=false) // For extra info
	private String jobDescription; // Maps to a field called job_description
	@Column(nullable=false) // For extra info
	private LocalDate jobFirstpublished; // Maps to a field called job_firstpublished
	@Column(nullable=false) // For extra info
	private boolean jobActive; // Maps to a field called job_active
	@Column(nullable=true) // For extra info
	private String jobWinner; // Maps to a field called job_winner
	
	@ManyToOne
	@JoinColumn(name="userId") // Foreign key userId
	private User user;
	
	@OneToMany(mappedBy="job", fetch=FetchType.EAGER, cascade=CascadeType.ALL) // A job can have many Bids (List of bids)
	@JsonIgnore
	private List<Bid> bids;
	
	//private Scheduler scheduler = new Scheduler();
	
	public Job(String jobName, String jobDescription, User user) {
		this.jobName=jobName;
		this.jobDescription=jobDescription;
		this.jobFirstpublished=LocalDate.now(); // The date when it is created
		this.user=user;
		this.jobActive=true; // At the beginning the job is active, it won't after 20 days (will be closed)
		this.jobWinner = null;
	}
	
	public void isActive() {
		LocalDate today=LocalDate.now();
		long daysBetween = ChronoUnit.DAYS.between(jobFirstpublished, today);
		//System.out.println(daysBetween);
		
		if(daysBetween >= 20) { // Close the job after 20 days
			setJobActive(false);
		}else {
			setJobActive(true);
		}
	}
	
	public void getWinner() {
		if(!this.jobActive) {
			this.jobWinner = this.getUser().getUserName() + " " + this.getUser().getUserLastname();
		}else {
			this.jobWinner = null;
		}
	}
}