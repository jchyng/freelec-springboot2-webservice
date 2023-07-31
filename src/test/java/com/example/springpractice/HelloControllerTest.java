package com.example.springpractice;

import com.example.springpractice.web.HelloController;
import org.apache.catalina.security.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/*
* @SpringBootTest가 아닌 @RunWith(SpringRunner.class)를 사용한 이유
* @SpringBootTest는 application context를 전부 로딩해서 무겁다
* @RunWith(Spring.class)는 @Autowired, @MockBean에 해당하는 것들만 application context에 로딩하게 되므로 가볍다
* springBootTest와 Junit의 연결자 역할을 한다.
* */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
            excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
            })
//spring MVC에 대한 기능들을 지원 = Controller, 단 service, component, repository 등은 지원하지 않는다.
//WebMvcTest는 SecurityConfig를 생성하기 위한 CustomOAuth2UserService를 스캔하지 않는다. -> 스캔 대상에서 securityConfig 제외
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;    //Web API 테스트에 사용 = HTTP, GET, POST 등 사용 가능

    @WithMockUser("USER")
    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        //mvc.perform = get에 있는 주소로 GET 요청을 보낸다. 체이닝이 지원되어 여러 검증 기능을 이어서 선언할 수 있다.
        mvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(status().isOk()) //status가 200인지 아닌지 검증 -> 400, 500 이라면 에러
                .andExpect(content().string(hello));    //Controller에서 hello를 return하기 때문에 값이 맞는지 검증
    }

    @WithMockUser("USER")
    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(MockMvcRequestBuilders.get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))    //jsonPath JSON 응답값을 필드별로 검증할 수 있는 메소드
                .andExpect(jsonPath("$.amount", is(amount)));   //$를 기준으로 필드명을 명시
    }
}
