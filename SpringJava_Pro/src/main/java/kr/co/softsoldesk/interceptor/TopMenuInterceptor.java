package kr.co.softsoldesk.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.softsoldesk.beans.BoardInfoBean;
import kr.co.softsoldesk.service.TopMenuService;

public class TopMenuInterceptor implements HandlerInterceptor{

	
	private TopMenuService topMenuService;
	
	public TopMenuInterceptor(TopMenuService topMenuService) {
		//interceptor는 오토와이어드가 되지 않음!
		this.topMenuService = topMenuService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		List<BoardInfoBean> topMenuList = topMenuService.getTopMenuList();
		
		request.setAttribute("topMenuList", topMenuList);
		return true;
	}
}
