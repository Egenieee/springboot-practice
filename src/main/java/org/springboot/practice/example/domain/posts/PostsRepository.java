package org.springboot.practice.example.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// Dao라고 불리는 DB Layer 접근자이다.
// 단순히 인터페이스를 생성 후에, JpaRepository<Entity class, PK 타입>를 상속하면 기본적인 CRUD 메서드가 자동으로 생성된다.
// @Repository 어노테이션을 따로 추가할 필요 없다. 여기서 주의할 점은 Entity 클래스와 기본 Entity Repository는 함께 위치해야 한다.
// 따라서 엔티티와 리포지토리는 함께 움직여야 하므로 도메인 패키지에서 함께 관리한다.
public interface PostsRepository extends JpaRepository<Posts, Long> {

}
