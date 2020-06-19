package com.universitysys.SSE.controller;

import com.universitysys.SSE.model.Account;
import com.universitysys.SSE.model.Students;
import com.universitysys.SSE.service.LoginService;
import com.universitysys.SSE.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("name")
public class RegisterController {
    @Autowired
    public RegisterService service;

    @Autowired
    public LoginController controller;

    @Autowired
    public LoginService loginService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView addUser(ModelMap model,HttpServletRequest request, HttpServletResponse response,
        @ModelAttribute("students") Students students,@ModelAttribute("account")Account account, @RequestParam String username, @RequestParam String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        boolean isValidStudent =loginService.validateUsername(  username );
        if (!isValidStudent) {
            service.registerStudent(students);
            loginService.registerAccount(account);
                    return new ModelAndView("index");
        }

        else {
            model.addAttribute("errorMessage", "Error: Invalid Credentials");
            return new ModelAndView("register");

        }
    }





}

