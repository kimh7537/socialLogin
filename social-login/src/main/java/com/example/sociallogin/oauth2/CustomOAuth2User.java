package com.example.sociallogin.oauth2;

import com.example.sociallogin.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final LoginDto loginDto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return loginDto.getRole();
            }
        });
        return collection;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    public String getUniqueId(){
        return loginDto.getUniqueId();
    }

    @Override
    public String getName() {
        return loginDto.getName();
    }
}
