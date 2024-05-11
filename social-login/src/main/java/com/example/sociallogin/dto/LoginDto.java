package com.example.sociallogin.dto;

import com.example.sociallogin.domain.Role;
import com.example.sociallogin.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginDto {
    private String uniqueId;
    private String name;
    private String role;

    public LoginDto(User user){
        this.uniqueId = user.getUniqueId();
        this.name = user.getName();
        this.role = user.getRoleKey();
    }

    @Builder
    public LoginDto(String uniqueId, String role){
        this.uniqueId = uniqueId;
        this.role = role;
    }
}
