package org.springboot.practice.example.web;

import lombok.RequiredArgsConstructor;
import org.springboot.practice.example.config.auth.dto.SessionUser;
import org.springboot.practice.example.service.posts.PostsService;
import org.springboot.practice.example.web.dto.PostsResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user"); // CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성했다.
        // 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있다.

        if (user != null) { // 세션에 저장된 값이 있을 때만 model에 userName으로 등록한다. 세션에 저장된 값이 없으면 model엔 아무런 값이 없는 상태이니 로그인 버튼이 보이게 된다.
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {

        PostsResponseDto responseDto = postsService.findById(id);
        model.addAttribute("post", responseDto);

        return "posts-update";
    }
}

// 머스테치에 URL을 매핑해보자
// 머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정된다.
// 앞의 경로는 src/main/resources/templates으로, 뒤의 파일 확장자는 .mustache가 붙는다.

// Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
// 여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달한다.
// model은 HashMap 형태를 갖고 있어 key값과 value값처럼 사용할 수 있다. 따라서 posts라는 속성에 이것에 대한 값으로 postsService.findAllDesc()을 주었다.
// 넘긴 model에서 머스테치를 값을 key값으로 받아 사용할 수 있다.