package org.springboot.practice.example.web;

import lombok.RequiredArgsConstructor;
import org.springboot.practice.example.service.posts.PostsService;
import org.springboot.practice.example.web.dto.PostsSaveRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor //final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성해준다.
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }
}