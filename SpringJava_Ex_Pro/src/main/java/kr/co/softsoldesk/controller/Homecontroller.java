package kr.co.softsoldesk.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.softsoldesk.beans.UserBean;

@Controller
public class Homecontroller {

	@Resource(name="loginUserBean")
	private UserBean loginUserBean; 
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
		System.out.println();
		return "redirect:/main"; 
		// ServletAppContext.java에서 설정해주었기에 index만사용된다  index.jsp를쓰면 index.jsp.jsp가된다
	}
}
