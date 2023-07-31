package com.example.springpractice.config.auth.dto;

import com.example.springpractice.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    //Session에는 인증된 사용자의 정보만 있으면 된다.
    private String name;
    private String email;
    private String picture;


    /*User 클래스가 아닌 Dto 클래스를 따로 만든 이유 -> 직렬화를 구현하지 않으면 type convert 에러가 발생하게 된다.
    * 이를 해결하기 위해 User class에 직렬화 코드를 넣는다면? -> Entity 클래스이기 때문에 언제 다른 관계가 형성될 지 모른다.
    * @ManyToOne, OneToMany 등 자식 엔티티를 갖고있다면 직렬화 대상에 자식들까지 포함되니 성능 이슈, 부수 효과가 발행할지도 모른다.
    * 그렇기 때문에 유지보수 측면에서 직렬화 기능을 가진 Session Dto 클래스를 만드는 것이 좋다.*/

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
