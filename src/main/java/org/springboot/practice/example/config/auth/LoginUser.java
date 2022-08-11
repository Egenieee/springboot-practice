package org.springboot.practice.example.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 이 어노테이션이 생성될 수 있는 위치를 지정한다. PARAMETER로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용할 수 있다. 이외에도 클래스 선언문에 쓸 수 있는 TYPE 등이 있다.
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser { // 이 파일을 어노테이션 클래스로 지정한다.
}

// index 메서드 외의 다른 컨트롤러와 메서드에서 세션값이 필요하면 그 때마다 직접 세션에서 값을 가져와야 한다.
// 같은 코드가 계속해서 반복되는 것은 불필요하기 때문에, 이 부분을 메소드 인자로 세션값을 바로 받을 수 있도록 어노테이션을 추가해 변경해보자.
