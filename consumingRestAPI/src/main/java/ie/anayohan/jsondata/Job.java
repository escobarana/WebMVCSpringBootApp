package ie.anayohan.jsondata;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {

	private int jobId;
	private String jobName;
	private String jobDescription;
	private LocalDate jobFirstpublished; 
	private boolean jobActive; 
	private String jobWinner;
	private User user;
}
