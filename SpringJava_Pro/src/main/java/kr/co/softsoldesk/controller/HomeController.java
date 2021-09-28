package kr.co.softsoldesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String home() {
		return "redirect:/main"; 
		// ServletAppContext.java에서 설정해주었기에 index만사용된다  index.jsp를쓰면 index.jsp.jsp가된다
	}

}
