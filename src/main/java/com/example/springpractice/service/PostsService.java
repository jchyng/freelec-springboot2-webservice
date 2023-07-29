package com.example.springpractice.service;

import com.example.springpractice.domain.posts.Posts;
import com.example.springpractice.domain.posts.PostsRepository;
import com.example.springpractice.web.dto.PostsListResponseDto;
import com.example.springpractice.web.dto.PostsResponseDto;
import com.example.springpractice.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+id));

        /*update 쿼리가 없이도 업데이트가 된다. => JPA의 영속성 컨텍스트 때문
        *영속성 컨텍스트란 엔티티를 영구저장하는 환경이다. 일종의 논리적 개념이며, JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈린다.
        * EntityManager가 활성화된 상태로 트랜잭션 안에서 데이터베이스에서 데이터를 가져오려면 데이터는 영속성 컨텍스트가 유지된 상태이다.
        * 이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에서 해당 테이블에 변경분을 반영한다.
        * 즉, Entity 객체의 값만 변경하면 별도의 update 쿼리를 날릴 필요가 없다. = 이 개념을 더티 체킹이라고 한다.= 상태변화 체크라고 보면된다.
        * */
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+ id));
        return new PostsResponseDto(entity);
    }

    //readOnly를 주면 트랜잭션 범위는 유지하되 조회 기능만 남겨두어, 조회 속도가 개선되기 때문에 등록, 수정, 삭제 기능이 없을 때 사용하는 것을 추천한다.
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) //posts -> new PostsListResponseDto(posts)와 같다
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 싸용자가 없습니다. id=" + id));
        postsRepository.delete(posts);
    }
}
