package com.example.sociallogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogoutController {

    @GetMapping("/admin")
    @ResponseBody
    public String myAPI() {
        return "admin route";
    }
}
