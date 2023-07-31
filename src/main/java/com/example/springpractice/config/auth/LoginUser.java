package com.example.springpractice.config.auth;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)  //어노테이션이 생성될 수 있는 위치 -> 메소드의 파라미터에서 사용 가능
@Retention(RetentionPolicy.RUNTIME) //어느시점까지 어노테이션의 메모리를 가져갈 지 설정
public @interface LoginUser {

}
