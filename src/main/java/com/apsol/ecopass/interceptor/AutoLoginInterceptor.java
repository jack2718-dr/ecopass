package com.apsol.ecopass.interceptor;

import com.apsol.ecopass.component.AuthenticationProviderImpl;
import com.apsol.ecopass.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * 테스트용 자동 로그인 인터셉터입니다.
 */
@Component
public class AutoLoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AutoLoginInterceptor.class);

    @Autowired
    EmployeeService employeeService;

    @Autowired
    AuthenticationProviderImpl authenticationProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

        if (trustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {

            Authentication authentication = new Authentication() {
                private static final long serialVersionUID = 4470780516744965992L;
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return null;
                }
                @Override
                public Object getCredentials() {
                    return "0000";
                }
                @Override
                public Object getDetails() { return null; }
                @Override
                public Object getPrincipal() { return "shlee"; }
                @Override
                public boolean isAuthenticated() {
                    return false;
                }
                @Override
                public void setAuthenticated(boolean b) throws IllegalArgumentException { }
                @Override
                public String getName() {
                    return null;
                }
            };

            Authentication authenticationResult = authenticationProvider.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);

            logger.info("*****************자동로그인 처리됨*****************");
            logger.info("*****************자동로그인 처리됨*****************");
            logger.info("*****************자동로그인 처리됨*****************");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
