package com.example.sociallogin.controller;

import com.example.sociallogin.dto.EmailRequestDto;
import com.example.sociallogin.dto.EmailResponseDto;
import com.example.sociallogin.oauth2.EmailSendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailSendService emailSendService;

    @GetMapping("/test")
    public String helloTest(){
        return "Hello World";
    }

    /* Send Email: 인증번호 전송 버튼 click */
    @PostMapping("/signup")
    public Map<String, String> mailSend(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        String code = emailSendService.joinEmail(emailRequestDto.getEmail());
        // response를 JSON 문자열으로 반환
        Map<String, String> response = new HashMap<>();
        response.put("code", code);

        return response;
    }

    /* Email Auth: 인증번호 입력 후 인증 버튼 click */
    @PostMapping("/signup/emailAuth")
    public String authCheck(@RequestBody @Valid EmailResponseDto emailResponseDto) {
        Boolean checked = emailSendService.checkAuthNum(emailResponseDto.getEmail(), emailResponseDto.getAuthNum());
        if (checked) {
            return "이메일 인증 성공!";
        }
        else {
            throw new NullPointerException("이메일 인증 실패!");
        }
    }

}

