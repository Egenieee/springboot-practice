package org.springboot.practice.example.config.auth;

import lombok.RequiredArgsConstructor;
import org.springboot.practice.example.config.auth.dto.OAuthAttributes;
import org.springboot.practice.example.config.auth.dto.SessionUser;
import org.springboot.practice.example.domain.user.User;
import org.springboot.practice.example.domain.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession; // 로그인 유지를 위한 세션. 로그인 정보를 세션에 담고, 세션을 이요해서 로그인 여부를 검증할 수 있고 회원의 정보를 언제든지 불러올 수 있다.

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId(); // 현재 로그인 진행 중인 서비스를 구분하는 코드이다. 네이버인지, 구글인지 구분하기 위해 사용한다.

        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // OAuth2 로그인 진행 시 키가 되는 필드값을 이야기한다. primary key와 같은 의미이다.
                                            // 구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 기본 지원하지 않는다. 구글의 기본코드는 "sub"이다.
                                            // 이후 네이버 로그인과 구글 로그인을 동시 지원할 때 사용된다.

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes()); // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스이다.

        User user = saveOrUpdate(attributes);

        // CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성했다.
        httpSession.setAttribute("user", new SessionUser(user)); // 세션에 사용자 정보를 저장하기 위한 Dto 클래스이다.

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}

// 이 클래스는 구글 로그인 이후 가져온 사용자의 정보(email, name, picture)들을 기반으로 가입 및 정보수정, 세션 저장 등의 기능을 지원한다.