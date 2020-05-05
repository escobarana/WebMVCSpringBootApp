package ie.anayohan.jsondata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private int userId;
	private String userName;
	private String userLastname;
	private String userEmail;
	private String userPassword;
	private String userPhone;
}
