package com.example.springpractice;

import com.example.springpractice.web.ProfileController;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ProfileControllerUnitTest {

    //Environment는 인터페이스라 구현체인 MockEnvironment를 통해 테스트할 수 있다.
    @Test
    public void real_profile이_조회된다(){
        //given
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        //생성자 DI가 중요한 이유 -> @Autowired를 사용했다면 항상 스프링 테스트를 해야한다.
        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void real_profile이_없으면_default가_조회된다(){
        //given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        ProfileController controller = new ProfileController(env);
        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}
