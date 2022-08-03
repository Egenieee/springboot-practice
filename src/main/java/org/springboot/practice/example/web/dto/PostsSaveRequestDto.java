package org.springboot.practice.example.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springboot.practice.example.domain.posts.Posts;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}

// Entity 클래스와 거의 유사한 형태임에도 Dto 클래스를 추가로 생성했다.
// 하지만 절대로 Entity 클래스를 Request/Response 클래스로 사용해서는 안된다.
// Entity 클래스는 데이터베이스와 맞닿은 핵심 클래스인데,
// 화면 변경을 위해서 테이블과 연결된 Entity 클래스를 변경하는 것은 너무 큰 변경이기 때문이다.
// 여러 서비스 클래스나 비즈니스 로직들이 Entity 클래스를 기준으로 동작하기 때문에 이 클래스가 변경되면 다른 여러 클래스들에 영향을 끼치지만,
// Request와 Response용 Dto는 View를 위한 클래스라 정말 자주 변경이 필요하다.
// 따라서 View Layer와 DB Layer의 역할 분리는 철저하게 하는 것이 좋다.
