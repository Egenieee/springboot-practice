package org.springboot.practice.example.web;

import lombok.RequiredArgsConstructor;
import org.springboot.practice.example.service.posts.PostsService;
import org.springboot.practice.example.web.dto.PostsResponseDto;
import org.springboot.practice.example.web.dto.PostsSaveRequestDto;
import org.springboot.practice.example.web.dto.PostsUpdateRequestDto;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor //final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성해준다.
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) { // 게시글 등록
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) { // 게시글 수정
        return postsService.update(id, requestDto);
    }
}