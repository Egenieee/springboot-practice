package org.springboot.practice.example.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 빈(Bean)을 주입 받는다.
    private MockMvc mvc; // 웹 api를 테스트할 떄 사용한다. 이 클래스를 통해 http method에 대한 api 테스트를 할 수 있다.

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // MockMvc를 통해 /hello 주소로 http get 요청을 한다.
                .andExpect(status().isOk()) // mvc.perform의 결과를 검증한다. 200, 404 등의 상태를 검증한다. 여기선 Ok 즉 200인지 아닌지 검증한다.
                .andExpect(content().string(hello)); // 응답 본문의 내용을 검증한다. Controller에서 "hello"를 반환하기 때문에 이 값이 맞는지 검증한다.
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name) // api test 시 사용될 요청 파라미터를 설정한다. 값은 String만 허용된다.
                .param("amount", String.valueOf(amount))) // 숫자/날짜 등의 데이터도 문자열로 변경하여 등록해야 한다.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // json 응답값을 필드별로 검증할 수 있는 메서드이다. $를 기준으로 필드명을 명시한다.
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
