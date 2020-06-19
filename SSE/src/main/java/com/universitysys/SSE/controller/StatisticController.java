package com.universitysys.SSE.controller;

import com.universitysys.SSE.exception.WrongPasswordOrLogin;
import com.universitysys.SSE.model.Account;
import com.universitysys.SSE.model.Students;
import com.universitysys.SSE.repository.StatisticRepository;
import com.universitysys.SSE.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class StatisticController {
    @Autowired
    public StatisticService statisticService;

    @RequestMapping(value = "/statistics" , method = RequestMethod.GET)
    public ModelAndView messages() {
        ModelAndView mav = new ModelAndView("statistics");
        mav.addObject("messages",  statisticService.showInfo());
        return mav;
    }
    @ModelAttribute("messages")
    public List<Students> message() {
        return statisticService.showInfo();
    }


}
