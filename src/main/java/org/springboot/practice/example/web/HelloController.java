package org.springboot.practice.example.web;

import org.springboot.practice.example.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러를 json을 반환하는 컨트롤러로 만들어준다.
public class HelloController {

    @GetMapping("/hello")
    // http method인 get의 요청을 받을 수 있는 api를 만들어 준다.
    // /hello로 요청이 오면 문자열 "hello"를 반환하는 기능을 가지게 된다.
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    // RequestParam은 외부에서 Api로 넘긴 파라미터를 가져오는 어노테이션이다.
    // 외부에서 넘긴 파라미터를 메서드 파라미터 name(String name), amount(int amount)에 각각 저장하게 된다.
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
