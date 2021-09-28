package kr.co.softsoldesk.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.softsoldesk.interceptor.TopMenuInterceptor;
import kr.co.softsoldesk.mapper.BoardInfoMapper;
import kr.co.softsoldesk.mapper.TopMenuMapper;
import kr.co.softsoldesk.service.TopMenuService;
//프로젝트 전반적인설정
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "kr.co.softsoldesk.controller")
@ComponentScan("kr.co.softsoldesk.dao")
@ComponentScan("kr.co.softsoldesk.service")
@PropertySource("/WEB-INF/properties/db.properties")
public class ServletAppContext implements WebMvcConfigurer{
	
	
	@Value("${db.classname}")
	private String db_classname;

	@Value("${db.url}")
	private String db_url;
		
	@Value("${db.username}")
	private String db_username;
		
	@Value("${db.password}")
	private String db_password;
	
	@Autowired
	private TopMenuService topMenuService;
	
	//controller의 메서드가 반환하는 결과값을 view로 보낼때 지정되어지는 경로 구현
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.configureViewResolvers(registry); 
		registry.jsp("/WEB-INF/views/" , ".jsp"); //폴더 , 파일명
	}
	
	//정적 파일(데이터)의 경로 지정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/"); //**어디에 있든지
	}
	
	// 데이터베이스 접속 정보를 관리하는 Bean
		@Bean
		public BasicDataSource dataSource() {
			BasicDataSource source = new BasicDataSource();
			source.setDriverClassName(db_classname);
			source.setUrl(db_url);
			source.setUsername(db_username);
			source.setPassword(db_password);
			
			return source;
		}
	
		// 쿼리문과 접속 정보를 관리하는 객체
		@Bean
		public SqlSessionFactory factory(BasicDataSource source) throws Exception{
			SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
			factoryBean.setDataSource(source);
			SqlSessionFactory factory = factoryBean.getObject();
			return factory;
		}
	
		// 쿼리문 실행을 위한 객체(Mapper 관리)
		@Bean
		public MapperFactoryBean<BoardInfoMapper> getBoardInfoMapper(SqlSessionFactory factory) throws Exception{
			MapperFactoryBean<BoardInfoMapper> factoryBean = new MapperFactoryBean<BoardInfoMapper>(BoardInfoMapper.class);
			factoryBean.setSqlSessionFactory(factory);
			return factoryBean;
		}
	
		@Bean
		public MapperFactoryBean<TopMenuMapper> getTopMenuMapper(SqlSessionFactory factory) throws Exception{
			MapperFactoryBean<TopMenuMapper> factoryBean = new MapperFactoryBean<TopMenuMapper>(TopMenuMapper.class);
			factoryBean.setSqlSessionFactory(factory);
			return factoryBean;
		}
	
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			// TODO Auto-generated method stub
			WebMvcConfigurer.super.addInterceptors(registry);
			
			TopMenuInterceptor topMenuInterceptor = new TopMenuInterceptor(topMenuService);
			
			InterceptorRegistration reg1 = registry.addInterceptor(topMenuInterceptor);
			reg1.addPathPatterns("/**");
		}
		
		//같은 프로퍼티가 올라왔을때 따로 관리해줌
		//error message 선언과 충돌되므로 별도로 관리해야 한다.
		@Bean
		public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
			
			return new PropertySourcesPlaceholderConfigurer();
		}
		
		@Bean
		public ReloadableResourceBundleMessageSource messageSource() {
			ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
			res.setBasename("WEB-INF/properties/error_message");
			
			return res;
		}
}
