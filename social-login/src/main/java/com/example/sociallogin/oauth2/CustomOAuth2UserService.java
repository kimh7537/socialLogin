package com.example.sociallogin.oauth2;

import com.example.sociallogin.domain.User;
import com.example.sociallogin.dto.LoginDto;
import com.example.sociallogin.dto.OAuth2Attribute;
import com.example.sociallogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //naver, google
        OAuth2Attribute attribute = OAuth2Attribute.of(registrationId, oAuth2User.getAttributes());

        String uniqueId = registrationId+" "+attribute.getProviderId();

        User user = saveOrUpdate(attribute, uniqueId);
        LoginDto loginDto = new LoginDto(user);

        return new CustomOAuth2User(loginDto);
    }

    private User saveOrUpdate(OAuth2Attribute attribute, String uniqueId){
        User user = userRepository.findByUniqueId(uniqueId)
                .map(entity -> entity.update(attribute.getEmail(), attribute.getName()))
                .orElse(attribute.toEntity(uniqueId));
        return userRepository.save(user);
    }
}
