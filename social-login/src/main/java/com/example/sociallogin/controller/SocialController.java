package com.example.sociallogin.controller;

import com.example.sociallogin.dto.LoginDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class SocialController {

    @GetMapping("/")
    @ResponseBody
    public String mainAPI() {
        return "main route";
    }

}
