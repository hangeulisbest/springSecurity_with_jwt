package com.memo.backend.controller;

import com.memo.backend.dto.login.LoginReqDTO;
import com.memo.backend.dto.member.MemberReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/login")
    public String getlogin(Model model) {
        model.addAttribute("loginReqDTO",new LoginReqDTO());
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(LoginReqDTO loginReqDTO) {
        log.debug("LOG!! IN !! = {}",loginReqDTO);
        return "admin/main";
    }
}
