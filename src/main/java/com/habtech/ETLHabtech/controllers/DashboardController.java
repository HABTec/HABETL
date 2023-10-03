package com.habtech.ETLHabtech.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value ={"/user","/user/"})
public class DashboardController {
    @GetMapping
    public String getDashboard(Model model){
        return "/user/index";
    }
}
