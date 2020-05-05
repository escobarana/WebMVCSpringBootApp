package ie.anayohan.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Tells Spring that User is a JPA entity which will be mapped to a table in the database called user. Maps to a table called user
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	
	@Id // Marks this as the primary key
	private String userEmail;
	
	@Column
	private String roleDescription;
}
