package com.example.springpractice.domain.posts;

import com.example.springpractice.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter //Entity 클래스에서는 Setter를 생성하지 않는다.
@NoArgsConstructor
@Entity //기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 매칭
public class Posts extends BaseTimeEntity { //시간관리를 위해 BaseTimeEntity 상속
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //웬만하면 타입의 Auto_Increment 권장 = 비즈니스 상 유니크 키는 pk보다는 유니크 키로 별도 활용

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    //실제 비즈니스 처리를 하는 곳은 도메인이다.
    //service는 트랜잭션과 도메인 간의 순서만 보장해준다.
    //만일 service에서 비즈니스 처리를 한다면 도메인 객체는 단순히 데이터 덩어리 역할만 하게된다.
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
