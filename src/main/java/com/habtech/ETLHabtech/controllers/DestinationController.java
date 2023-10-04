package com.habtech.ETLHabtech.controllers;

import com.habtech.ETLHabtech.models.Destination;
import com.habtech.ETLHabtech.models.DestinationType;
import com.habtech.ETLHabtech.services.DestinationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user/destination")
public class DestinationController {
    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public String indexDestination(Model model){
        model.addAttribute("destinations",destinationService.getAllDestinations());
        return "user/destination/index";
    }

    @GetMapping("create")
    public String createDestination(Model model){
        List<DestinationType> types = Arrays.asList(DestinationType.values());
        model.addAttribute("destinationTypes", types);
        return "user/destination/create";
    }
    @PostMapping("store")
    public String postDestination(@ModelAttribute("destination") Destination destination, RedirectAttributes attributes){
        destinationService.createDestination(destination);
        // attributes.addAttribute("","");//TODO add a notification
        return "redirect:/user/destination";
    }
}
