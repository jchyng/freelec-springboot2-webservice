package com.example.springpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing  //JPA auditing 활성화  -> JpaAuditing을 사용하기 위해선 최소 1개의 entity가 필요하지만 WebMvcTest에선 entity가 없기 때문에 config 파일에서 따로 적용
@SpringBootApplication  //이 어노테이션을 기점으로 설정을 읽어들이기 때문에 해당 파일은 항상 루트 폴더에 위치해야한다.
public class SpringPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPracticeApplication.class, args);
    }

}
