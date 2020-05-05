package ie.anayohan.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Tells Spring that User is a JPA entity which will be mapped to a table in the database called user. Maps to a table called user
@Data
@NoArgsConstructor
public class User {
	@Id // Marks this as the primary key
	@GeneratedValue // Id's will be generated automatically
	private int userId; // Maps to a field called user_id
	
	@Column(nullable=false) // For extra info
	private String userName; // Maps to a field called user_name
	
	@Column(nullable=false) // For extra info
	private String userLastname; // Maps to a field called user_lastname
	
	@Column(nullable=false, unique=true) // For extra info
	@Email
	private String userEmail; // Maps to a field called user_email
	
	@Column(nullable=false) // For extra info
	@Size(min=8)
	private String userPassword; // Maps to a field called user_password
	
	@Column(nullable=false, unique=true) // For extra info
	private String userPhone; // Maps to a field called user_phone
	
	@Column // For the security part
	private boolean userEnabled;
	
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER, cascade=CascadeType.ALL) // A user can have many Jobs (List of jobs)
	@JsonIgnore
	private List<Job> jobs;
	
	@OneToOne
	@JoinColumn(name="roleEmail", nullable=false)
	@JsonIgnore
	Role userRole; // The email is the user username
	
	/**
	 * I create my own constructor which injects a user name into the class but leaves the ID alone.
	 * When the object is committed to the database, the database will generate its own ID as per @GeneretedValue 
	 * 
	 */
	public User(String userName, String userLastName, String userEmail, String userPassword, String userPhone, Role userRole, boolean userEnabled) {
		this.userName=userName;
		this.userLastname = userLastName;
		this.userEmail=userEmail;
		this.userPassword=userPassword;
		this.userPhone=userPhone;
		this.userRole=userRole;
		this.userEnabled=userEnabled;
	}
}