package kr.co.softsoldesk.beans;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBean {
	
	private int user_idx;
	
	@Size(min = 2, max = 4)
	@Pattern(regexp = "[가-힇]*")
	private String user_name;
	
	@Size(min = 4, max = 12)
	@Pattern(regexp = "[a-zA-Z0-9]*")
	private String user_id;
	
	@Size(min = 8, max = 25)
	@Pattern(regexp = "[a-zA-Z0-9]*")
	private String user_pw;
	
	@Size(min = 8, max = 25)
	@Pattern(regexp = "[a-zA-Z0-9]*")
	private String user_pw2;
}
