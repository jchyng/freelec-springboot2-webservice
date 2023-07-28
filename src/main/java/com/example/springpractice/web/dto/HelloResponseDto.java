package com.example.springpractice.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor    //final로 선언된 모든 필드가 포함된 생성자를 생성
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
