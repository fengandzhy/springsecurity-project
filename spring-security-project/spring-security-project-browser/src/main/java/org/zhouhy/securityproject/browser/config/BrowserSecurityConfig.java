package org.zhouhy.securityproject.browser.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zhouhy.securityproject.browser.authentication.MyAuthenticationSuccessHandler;
import org.zhouhy.securityproject.core.properties.SecurityProperties;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/authentication/request")
                .loginProcessingUrl("/authentication/form")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/request"
                        ,securityProperties.getBrowserProperties().getLoginPage()).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable();
    }
}
