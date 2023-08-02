package com.example.springpractice.config;

import com.example.springpractice.config.auth.CustomOauth2userService;
import com.example.springpractice.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity  //spring security 설정들을 활성화 시켜줌
public class    SecurityConfig {
    private final CustomOauth2userService customOauth2userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()    //h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .and()
                    .authorizeRequests()    //url 별 권한 설정 시작점 authorizeRequests()가 선언되어야만 antMatchers() 사용 가능
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile")  //antMathchers로 URL, HTTP Method 별 권한 관리가 가능
                    .permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())    //post면서 해당 url에서 Role이 user인 사람만 가능
                    .anyRequest()   //설정된 url외의 url들
                    .authenticated()    //권한을 줌
                .and()
                    .logout()   //Oauth2 로그아웃 기능에 대한 여러 설정의 진입점
                    .logoutSuccessUrl("/")// 로그아웃 시 "/"로 이동
                .and()
                    .oauth2Login()  //Oauth2 로그인 기능에 대한 여러 설정의 진입점
                    .userInfoEndpoint() //Oauth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당한다.
                    .userService(customOauth2userService);
                    //소셜 로그인 성공 시 후속 조치를 진행할 userService 인터페이스의 구현체를 등록
                    //소셜 서비스에서 사용자 정보를 가져온 상태에서 추가로 진행하고자하는 기능을 명시할 수 있다.
        return http.build();
    }
}
