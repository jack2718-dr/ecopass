package com.apsol.ecopass.config;

import com.apsol.ecopass.core.security.AuthenticationSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // default
    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

    // one-way crypto
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // two-way crypto
    @Bean
    public static TextEncryptor queryableTextEncryptor() {
        return Encryptors.queryableText("ApS0L@SerV*EC0", "23a35de329a183bf");
    }

    // session
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    // resource
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/img/**");
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/vendor/**");
        web.ignoring().antMatchers("/js/**");
    }

    // core
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable();
            //.ignoringAntMatchers("/api/**");
        http
            .headers()
            .frameOptions().sameOrigin()
            .httpStrictTransportSecurity().disable();
        http
            .authorizeRequests() // 기본 접근 권한
            .antMatchers("/admin/**").authenticated()
            .antMatchers("/master/**").authenticated()
            //.antMatchers("/admin/**").hasRole("ADMIN")
            //.antMatchers("/master/**").hasRole("MASTER")
            .antMatchers("/admin/login").permitAll()
            .antMatchers("/", "/**").permitAll()
            .antMatchers("/online", "/online/**").permitAll()
            .antMatchers("/api/**").permitAll()
            //.antMatchers("/master").permitAll()//
            .and()
            .formLogin() // 로그인 페이지 설정
            .loginPage("/admin/login")//.permitAll()
            .usernameParameter("username")
            .passwordParameter("password")
            .successHandler(new AuthenticationSuccessHandlerImpl())
                //.failureHandler()
                //.loginProcessingUrl()
            .permitAll()
            .and()
            .logout() // 로그아웃 설정
            .logoutUrl("/logout")
            .logoutSuccessUrl("/admin/login?logout")
            .invalidateHttpSession(true)					// 로그아웃시 인증정보 삭제
            .deleteCookies("JSESSIONID")					// 로그아웃시 쿠키 삭제
            .permitAll()
            .and()
            .httpBasic();
        http
            .sessionManagement() // 중복 로그인 설정
            .maximumSessions(1)								// 한명만
            .maxSessionsPreventsLogin(false)				// 중복접속시 기존 사용자를 유지한다(false)
            .sessionRegistry(sessionRegistry());
    }

}










































