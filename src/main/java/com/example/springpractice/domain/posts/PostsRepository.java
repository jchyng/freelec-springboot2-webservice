package com.example.springpractice.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//@Repository를 추가할 필요가 없다.
//나중에 서비스가 커졌을 때를 고려해 entity, repository 따로 패키지에 두는 것이 아닌 도메인 단위로 패키지를 나누고 entity와 Repository를 같이 둔다.
public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

}
