package com.example.sociallogin.oauth2;

import com.example.sociallogin.domain.RefreshEntity;
import com.example.sociallogin.jwt.JWTUtil;
import com.example.sociallogin.repository.RefreshRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public CustomSuccessHandler(JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String uniqueId = customUserDetails.getUniqueId();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String access = jwtUtil.createJwt("access", uniqueId, role, 600000L);
        String refresh = jwtUtil.createJwt("refresh", uniqueId, role, 86400000L);

        addRefreshEntity(uniqueId, refresh, 86400000L);

        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.sendRedirect("http://localhost:3000/");
    }

    private void addRefreshEntity(String uniqueId, String refresh, Long expiredMs) {

        //중복 저장 x 구현해야함
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUniqueId(uniqueId);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");  //모든 위치에서 쿠키를 볼 수 있음
        cookie.setHttpOnly(true);   //자바스크립트가 쿠키를 가져가지 못하게 함

        return cookie;
    }
}

