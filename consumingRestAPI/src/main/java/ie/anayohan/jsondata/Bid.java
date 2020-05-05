package ie.anayohan.jsondata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bid {

	private int bidId;
	private float bidValue; 
	private Job job;
	private User user;
}
