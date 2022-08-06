package org.springboot.practice.example.service.posts;

import lombok.RequiredArgsConstructor;
import org.springboot.practice.example.domain.posts.Posts;
import org.springboot.practice.example.domain.posts.PostsRepository;
import org.springboot.practice.example.web.dto.PostsResponseDto;
import org.springboot.practice.example.web.dto.PostsSaveRequestDto;
import org.springboot.practice.example.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
}

// 서비스 클래스에서는 트랜잭션과 도메인 간의 순서만 보장해준다.
// 스프링에서 Bean을 주입받을 때는 생성자로 주입받는 방식을 쓰는 것이 좋다.
// 이 때, lombok의 RequiredArgsConstructor를 사용하면 final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성해준다.
// 생성자를 직접 안 쓰고 롬복 어노테이션을 사용한 이유는 해당 클래스의 의존성 관계가 변경될 때마다 생성자 코드를 계속해서 수정하는 번거로움을 해결하기 위함이다.

// update 쿼리 없이 테이블 수정이 가능한 이유는 더티 체킹 때문이다.
// 더티 체킹이란 상태 변경 검사를 뜻한다. JPA에서는 트랜잭션이 끝나는 시점에 변화가 있는 모든 엔티티 객체를 데이터베이스에 자동으로 반영해준다.
// JPA 에서는 엔티티를 조회하면 해당 엔티티의 조회 상태 그대로 스냅샷을 만들어 놓고, 트랜잭션이 끝나는 시점에 이 스냅샷과 비교해서 다른 점이 있다면 update 쿼리를 데이터베이스로 전달한다.
// 당연히 이런 더티 체킹의 대상은 영속성 컨텍스트가 관리하는 엔티티에만 적용된다.