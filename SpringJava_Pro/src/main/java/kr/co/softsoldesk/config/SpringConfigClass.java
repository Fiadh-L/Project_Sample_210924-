package kr.co.softsoldesk.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

//요청처리의 기본적인 초기설정(예: 필터링, 인코딩, 리스너....)
public class SpringConfigClass implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		//System.out.println("onStartup");
		
		AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
		//객체 생성시에 지정했던 경로를 등록할 수 있는 객체가 많아지면서 이렇게 등록하는 방법으로 변경함!
		servletAppContext.register(ServletAppContext.class); //>>web.xml의 환경설정 역할
	
		//요청 발생시에 요청을 처리하고 서블릿에서 dispatcherServlet을 실행할때의 경로
		DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
		//viewresolve~ : dynamic 속성
		ServletRegistration.Dynamic servlet=servletContext.addServlet("dispatcher", dispatcherServlet);
		//dispatcher = key값 //dispatcher = value값
		
		//부가설정
		//맨처음에 가장 먼저 불러오는 값 설정 코드
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");//시작위치 설청 : 프로젝트클릭하고 컴파일하면 실행됨! 미쳤네... 쩐다 ("/":rootfile)
		
		//==============================================================================
		//Bean(VO)을 정의하는 클래스를 저장
		AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppContext.class);
		
		//listener
		ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
		servletContext.addListener(listener);
		

		//파라미터 인코딩 설정
		FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
		
		//추가 인코딩 :dispatcher에 의해서 추가된 servlet에도 적용
		filter.addMappingForServletNames(null, false, "dispatcher");
		
	}
	
	

}
