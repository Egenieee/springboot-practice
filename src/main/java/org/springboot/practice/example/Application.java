package org.springboot.practice.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @EnableJpaAuditing JPA Auditing 활성화 어노테이션 추가 -> 삭제 (이 어노테이션을 사용하기 위해선 적어도 하나의 엔티티(@Entity) 클래스가 필요하다)
// @WebMvcTest이다보니 엔티티 클래스가 없기 때문에 삭제 후 다른 클래스에 옮겨준다.
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
