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
	private TopMenuService TopMenuService;
	
	//controller의 메서드가 반환하는 결과값을 view로 보낼대 지정되어지는 경로 구현
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/",".jsp");
		
	}
	//정적 파일의 졍로 지정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
		//어디에 있던지 리소스만 있으면 됩니다.
	}
	
	@Bean
	public BasicDataSource datasource() {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName(db_classname);
		source.setUrl(db_url);
		source.setUsername(db_username);
		source.setPassword(db_password);
		
		return source;
	}
	// db연결
	@Bean
	public SqlSessionFactory factory(BasicDataSource source) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(source);
		
		SqlSessionFactory factory = factoryBean.getObject();
		return factory;
	}
	
	// 실행문
	@Bean
	public MapperFactoryBean<BoardInfoMapper> getBoardMapper(SqlSessionFactory factory) throws Exception {
		
		//쿼리
		MapperFactoryBean<BoardInfoMapper> factoryBean = new MapperFactoryBean<BoardInfoMapper>(BoardInfoMapper.class);
		//sql문을 입력받음 
		//오라클과 연동되어서 sql문을 해석하는 코드
		factoryBean.setSqlSessionFactory(factory);
		
		return factoryBean;
		
	}
	
	@Bean
	public MapperFactoryBean<TopMenuMapper> getTopMenuMapper (SqlSessionFactory factory) throws Exception{
		
		MapperFactoryBean<TopMenuMapper> factoryBean = new MapperFactoryBean<TopMenuMapper>(TopMenuMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		
		return factoryBean;
	}
	
	//interceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addInterceptors(registry);
		
		TopMenuInterceptor topMenuInterceptor = new TopMenuInterceptor(TopMenuService);
		
		InterceptorRegistration reg1 = registry.addInterceptor(topMenuInterceptor);
		reg1.addPathPatterns("/**");
	}
	
	
}
