package org.springboot.practice.example.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity // 데이터베이스 테이블과 링크될 클래스임을 나타낸다.
// 기본값으로 클래스의 카멜케이스 이름은 언더스코어 네이밍으로 테이블 이름을 매칭한다. (ex SalesManager -> sales_manager table)
public class Posts {

    @Id // 해당 테이블의 PK 필드를 나타낸다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 규칙을 나타낸다.
    private Long id;

    @Column(length = 500, nullable = false) // 테이블의 컬럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 된다.
    // 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용한다.
    // 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나, 타입을 TEXT로 변경하고 싶은 경우 등이 사용된다.
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스를 생성. 빌더를 사용하면 어느 필드에 어떤 값을 채워야 할지 명확하게 인지할 수 있다.
    // 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함됨.
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}

/*
 웬만하면 Entity의 PK는 Long 타입의 Auto_increment를 추천한다. (MySQL을 기준으로 이렇게 하면 bigint 타입이 된다)
 주민등록번호와 같이 유니크 키나 여러 키를 조합한 복합키로 PK를 잡을 경우 난간한 상황이 종종 발생한다.
 (1) FK를 맺을 때 다른 테이블에서 복합키 전부를 가지고 있거나, 중간 테이블을 하나 더 둬야 하는 상황이 발생한다.
 (2) 인덱스에 좋은 영향을 끼치지 못한다.
 (3) 유니크한 조건이 변경될 경우 PK 전체를 수정해야 하는 일이 발생한다.
  따라서 주민등록번호, 복합키 등은 유니크 키로 별도로 추가하는 것을 추천한다.

  Entity 클래스에서는 절대 Setter 메서드를 만들지 않는다.
  대신, 해당 필드의 값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 메서드를 추가해야만 한다.

  생성자 대신 빌더를 사용하여 값을 채운다.
  */
