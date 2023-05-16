package com.zangeus.bootstrap.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(name = "/")
public class AuthController {
    public String loginPage() {
        return "login";
    }
}
