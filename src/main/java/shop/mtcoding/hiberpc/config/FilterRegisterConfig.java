package shop.mtcoding.hiberpc.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.hiberpc.config.filter.MyBlackListFilter;

@Configuration
public class FilterRegisterConfig {

    @Bean
    public FilterRegistrationBean<?> blackListFilter() {

        FilterRegistrationBean<MyBlackListFilter> registraion = new FilterRegistrationBean<>();
        registraion.setFilter(new MyBlackListFilter());

        registraion.addUrlPatterns("/filter"); // 해당 필터가 실행될 url 패턴 지정
        registraion.setOrder(1); // 필터 여러개 만들어서 등록 시 필터 실행 순서 지정

        return registraion;
        // 필터를 등록하는 핵심 - FilterRegistrationBean 객체를 ioc 컨테이너에 넣는 것
        // 이를 spring에서 지원하는 방법 : @Bean
        // 내가 만든것이 아닌 객체는 해당 어노테이션으로 ioc에 띄우는 게 편함

    }
}
