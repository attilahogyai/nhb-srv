package com.nhb.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhb.config.SessionCache;
import com.nhb.domain.user.Useracc;

@Controller
public class HomeController extends AbstractController{

    @RequestMapping("/")
    public String index(Authentication authentication) {
		SessionCache session=handleLogin(authentication);
        return "greeting";
    }

}