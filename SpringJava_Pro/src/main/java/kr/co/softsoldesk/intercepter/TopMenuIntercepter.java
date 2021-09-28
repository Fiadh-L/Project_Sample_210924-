package kr.co.softsoldesk.intercepter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.softsoldesk.beans.BoardInfoBean;
import kr.co.softsoldesk.service.TopMenuService;

public class TopMenuIntercepter implements HandlerInterceptor {
	//Intercepter @Autowire가 사용불가능하므로 생성자를 통해서 초기화함
	 
	private TopMenuService topMenuService;
	
	public TopMenuIntercepter(TopMenuService topMenuService) {
		this.topMenuService=topMenuService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		List<BoardInfoBean> topMenulist=topMenuService.getTopMenuList();
		request.setAttribute("topMenuList", topMenulist);
		
		return true;
	}
}
