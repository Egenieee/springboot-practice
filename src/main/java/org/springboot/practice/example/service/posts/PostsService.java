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

        posts.update(posts.getTitle(), posts.getContent());

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
