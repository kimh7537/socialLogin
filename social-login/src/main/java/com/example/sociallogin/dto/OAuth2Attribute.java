package com.example.sociallogin.dto;

import com.example.sociallogin.domain.Role;
import com.example.sociallogin.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@Builder
public class OAuth2Attribute {

    private Map<String, Object> attributes;
    private String name;
    private String email;
    private String providerId;


    public static OAuth2Attribute of(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equals("google")){
            return ofGoogle(attributes);
        }
        return ofNaver(attributes);
    }

    private static OAuth2Attribute ofGoogle(Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .name(attributes.get("name").toString())
                .email(attributes.get("email").toString())
                .providerId(attributes.get("sub").toString())
                .attributes(attributes)
                .build();
    }

    private static OAuth2Attribute ofNaver(Map<String, Object> attributes) {
        Map<String, Object> attributesMap = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .name(attributesMap.get("name").toString())
                .email(attributesMap.get("email").toString())
                .providerId(attributesMap.get("id").toString())
                .attributes(attributesMap)
                .build();
    }

    public User toEntity(String uniqueId){
        return User.builder()
                .name(name)
                .email(email)
                .uniqueId(uniqueId)
                .role(Role.USER)
                .build();
    }

}
