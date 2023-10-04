package com.habtech.ETLHabtech.controllers;

import com.habtech.ETLHabtech.models.Source;
import com.habtech.ETLHabtech.models.SourceType;
import com.habtech.ETLHabtech.services.SourceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;
import java.util.Arrays;

@Controller
@RequestMapping("/user/source")
public class SourceController {
    private final SourceService sourceService;

    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @GetMapping
    public String indexSources(Model model){
        model.addAttribute("sources",sourceService.getAllSources());
        return "user/source/index";
    }

    @GetMapping("create")
    public String createSource(Model model){
        model.addAttribute("sourceTypes", Arrays.asList(SourceType.values()));
        return "user/source/create";
    }

    @PostMapping("store")
    public String postSource(@ModelAttribute("source") Source source){
        sourceService.createSource(source);
        return "redirect:/user/source";

    }

}
