package com.myspring.security.mysecuritydemo.security;


import com.myspring.security.mysecuritydemo.service.CustomUserService;
import com.myspring.security.mysecuritydemo.service.MyFilterSecurityInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
  * @author lht
  * @doc   security 规则配置
  * @date 2018/6/1
*/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启security的注解模式
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
      * @author lht
      * @doc   加载自定义获得用户信息（根据username）
      * @date 2018/6/1
    */
    @Bean
    UserDetailsService customUserService(){ //注册UserDetailsService 的bean
        return new CustomUserService();
    }


    /**
      * @author lht
      * @doc   设置密码为明文（在实际项目中不行）
      * @date 2018/6/1
    */
    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()); //user Details Service验证

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll() //资源不限制
                .anyRequest().authenticated()//任何请求,登录后可以访问
                .and().headers().frameOptions().disable() //禁止iframe拦截（如果不禁用，页面不能使用iframe）
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll() //登录页面用户任意访问
                .and()
                .logout().permitAll(); //注销行为任意访问
     //关闭默认设置csrf跨域（该设置开启时，在登录页必须要有该值，不然报错，一般禁用，禁用后，登录页面可以是正常登录页面(但必须为post请求，是写死的)
     //)，不需要额外设置，只需要
     //action为.loginPage("/login") 里面的值即可）
     http.csrf().disable();

        // 范例
       /* http.authorizeRequests()
                //静态资源和一些所有人都能访问的请求
                .antMatchers("/css/**","/staic/**", "/js/**","/images/**").permitAll()
                .antMatchers("/", "/login","/session_expired").permitAll()
                //登录
                .and().formLogin()
                .loginPage("/login")
                .usernameParameter("userId")       //自己要使用的用户名字段
                .passwordParameter("password")     //密码字段
                .defaultSuccessUrl("/index")     //登陆成功后跳转的请求,要自己写一个controller转发
                .failureUrl("/loginAuthtictionFailed")  //验证失败后跳转的url
                //session管理
                .and().sessionManagement()
                .maximumSessions(1)                //系统中同一个账号的登陆数量限制
                .and().and()
                .logout()//登出
                .invalidateHttpSession(true)  //使session失效
                .clearAuthentication(true)    //清除证信息
                .and()
                .httpBasic();*/
    }
}
