package com.atguigu.atcrowdfunding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.io.IOException;
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration //扫描项目中所有组件进行管理
@EnableWebSecurity  //启用安全框架中的注解
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
       /* @Autowired
        private PasswordEncoder passwordEncoder;*/


    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());/*.passwordEncoder(passwordEncoder);//MD5加密*/

    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //实验一
        http.authorizeRequests()
                //设置匹配的资源放行
                .antMatchers("/static/**", "/welcome.jsp").permitAll()


                //实验六
                //匹配角色
                /*  .antMatchers("/level1/**").hasRole("学徒")
                 *//*.antMatchers("/level2/**").hasRole("大师")*//*
                //匹配权限
                .antMatchers("/level2/1").hasAuthority("太极拳")
                .antMatchers("/level2/2").hasAuthority("七伤拳")
                .antMatchers("/level2/3").hasAuthority("梯云纵")
                .antMatchers("/level3/**").hasRole("宗师")
                //剩余任何资源必须认证*/
                .anyRequest().authenticated();

        //实验二 登录认证不通过，跳转到主页面
        http.formLogin().loginPage("/welcome.jsp");

        //实验三
        http.formLogin().loginProcessingUrl("/login")
                .usernameParameter("loginacct")
                .passwordParameter("userpswd")
                .defaultSuccessUrl("/main");
        http.csrf().disable(); //不适用crsf的验证
        //实验五  注销登录
        http.logout().logoutUrl("/loginOut").logoutSuccessUrl("/welcome.jsp");


        //实验七
        //访问受限，应该跳转到正确的页面
        /* http.exceptionHandling().accessDeniedPage("/unauth");*/
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
//ajax请求
                    String msg = "403";
                    response.getWriter().write(msg);
                }else{

                    request.setAttribute("err", accessDeniedException.getMessage());
                    request.getRequestDispatcher("/WEB-INF/views/unauth.jsp").forward(request, response);
                }


            }
        });

        //实验八 记住密码
        http.rememberMe();
    }

    public static void main(String args[]) {
	/*	String s = new String("123");
		System.out.println(digest(s));
*/
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        System.out.println(bc.encode("123"));
    }

}