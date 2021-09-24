package kr.co.softsoldesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String Home() {
		
		//context클래스에 configureViewResolvers에 jsp는 다 읽으라고 등록해둬서 확장자명(.jsp)는 생략해야함
		return "redirect:/main";
	}
	
}
