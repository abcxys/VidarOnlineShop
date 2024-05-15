package vidar.websystem.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:/home/ec2-user/uploads/")
                .addResourceLocations("classpath:/uploads/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").   //可以被跨域的路径,/**表示无限制,
                allowedOrigins("http://localhost:8080"). //允许跨域的域名，如果值为*,则表示允许任何域名使用
                allowedMethods("*"). //允许任何方法，值可以为： "GET", "POST" ...
                allowedHeaders("*"). //允许任何请求头
                allowCredentials(true). //允许带cookie信息
                exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L); //maxAge(3600):表示3600秒内，不需要再发送预检验请求，是结果可以缓存的时长
    }
}
