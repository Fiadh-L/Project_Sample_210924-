package kr.co.softsoldesk.config;


import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


//요청처리의 기본적인 초기설정(예:필터링,인코딩, 리스터.......)

public class SpringConfigClass implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//System.out.println("onStartup"); 확인용 콘솔 출력
		
		AnnotationConfigWebApplicationContext servletAddContext=new AnnotationConfigWebApplicationContext();
		servletAddContext.register(ServletAppContext.class); //web.xml의 환경설정
		//기본설정에 ServletAppContext.class 끌고와 읽는다
		//implements WebApplicationInitializer web.xml을 대체해서 환경설정을 여기서한다
		
		//요청발생시에 요청을 처리하고 서블릿에서 DispatcherServlet을 실행할때 의 경로
		DispatcherServlet dispatcherServlet= new DispatcherServlet(servletAddContext);
		ServletRegistration.Dynamic servlet=servletContext.addServlet("dispatcher", dispatcherServlet);
	
		//부가설정
		servlet.setLoadOnStartup(1); //가장먼저 받아들이겠다는 코드
		servlet.addMapping("/"); //프로젝트 최상위부터 읽게 한다
	
	
	//========================================================
	
		//Bean(VO)을 정의하는 클래스를지정
		AnnotationConfigWebApplicationContext rootAppContext=new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppContext.class);
		
		//Listener 설정
		ContextLoaderListener listener=new ContextLoaderListener(rootAppContext);
		servletContext.addListener(listener);
	
		//파라미터 인코딩 설정
		FilterRegistration.Dynamic filter=servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
		
		//추가 인코딩 : dispatcher에 의해서 추가된 Servlet에도 적용
		filter.addMappingForServletNames(null, false, "dispatcher");
		
		
	}


/*
public class SpringConfigClass extends AbstractAnnotationConfigDispatcherServletInitializer{
	
	//DispatcherServlet 에 대한 맵핑
	@Override
	protected String[] getServletMappings() {
		
		return new String[] {"/"};
	}


	//Spring MVC프로젝트 설정
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {ServletAppContext.class};	
	}
	
	//프로젝트에서 상요할 Bean들을 정의
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootAppContext.class}; 
	}
	
	
	//파라미터 인코딩 필터
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter=new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		
		return new Filter[] {encodingFilter};
	}*/
	
	
	
	
	
	
	
}
