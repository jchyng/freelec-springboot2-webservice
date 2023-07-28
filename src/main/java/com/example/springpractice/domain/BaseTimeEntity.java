package com.example.springpractice.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //Entity들이 BaseTimeEntity를 상속할 경우 필드들(생성 시간, 수정 시간)도 컬럼으로 인식하게 된다.
@EntityListeners(AuditingEntityListener.class) //BaseTimeEntity에 auditing 기능을 포함시킨다.
public class BaseTimeEntity {
    //BaseTimeEntity는 모든 Entity의 상위 클래스가 되어 Entity들의 createdDate, modifiedDate를 자동으로 관리한다.

    @CreatedDate    //Entity가 생성되어 저장될 때 자동으로 시간이 저장된다.
    private LocalDateTime createDate;

    @LastModifiedDate   //조회한 Entity의 값을 변경할 때 시간이 자동으로 저장된다.
    private LocalDateTime modifiedDate;
}
