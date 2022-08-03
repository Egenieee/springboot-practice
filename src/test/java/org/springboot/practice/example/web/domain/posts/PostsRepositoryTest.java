package org.springboot.practice.example.web.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.practice.example.domain.posts.Posts;
import org.springboot.practice.example.domain.posts.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class) // 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다. 즉 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 한다.
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired // 스프링이 관리하는 bean을 주입받는다.
    PostsRepository postsRepository;

    @After // JUnit에서 단위 테스트가 끝날 때마다 수행되는 메서드를 지정한다.
    // 보통은 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용한다.
    // 여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 있어 다음 테스트 실행 시 테스트가 실패할 수 있다.
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder() // 테이블 posts에 insert/update 쿼리를 실행한다. id값이 있다면 update가, 없다면 insert 쿼리가 실행된다.
                .title(title)
                .content(content)
                .author("ab23202304@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll(); // 테이블 posts에 있는 모든 데이터를 조회해오는 메서드이다.

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}

// 이 테스트를 실행한 뒤, sql 쿼리를 로그로 보고싶다면, application.properties 파일에 코드 한 줄을 추가하여 확인할 수 있다
// spring.jpa.show_sql=true
// 로그에 출력되는 형태는 H2의 쿼리 문법이 적용되어 있는데, 이후 디버깅을 위해서 출력되는 쿼리 로그를 MySQL 버전으로 변경해보자.
// spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
