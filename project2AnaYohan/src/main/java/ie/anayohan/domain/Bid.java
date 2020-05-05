package ie.anayohan.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // Tells Spring that User is a JPA entity which will be mapped to a table in the database called user. Maps to a table called user
@Getter
@Setter
@NoArgsConstructor
public class Bid {

	@Id // Marks this as the primary key
	@GeneratedValue // Id's will be generated automatically
	private int bidId; // Maps to a field called bid_id
	
	@Column(nullable=false) // For extra info
	private float bidValue; // Maps to a field called bid_value
	
	@ManyToOne
	@JoinColumn(name="jobId") // Foreign key jobId
	private Job job;
	
	@ManyToOne
	@JoinColumn(name="userId") // Foreign key userId
	private User user;
	
	public Bid(float bidValue, Job job, User user) {
		this.bidValue=bidValue;
		this.job = job;
		this.user = user;
	}
}