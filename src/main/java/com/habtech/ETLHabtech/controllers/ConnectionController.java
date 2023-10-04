package com.habtech.ETLHabtech.controllers;

import com.habtech.ETLHabtech.models.Connection;
import com.habtech.ETLHabtech.services.ConnectionService;
import com.habtech.ETLHabtech.services.DestinationService;
import com.habtech.ETLHabtech.services.SourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/connection")
public class ConnectionController {
    private final ConnectionService connectionService;
    private final DestinationService destinationService;
    private final SourceService sourceService;

    public ConnectionController(ConnectionService connectionService, DestinationService destinationService, SourceService sourceService) {
        this.connectionService = connectionService;
        this.destinationService = destinationService;
        this.sourceService = sourceService;
    }

    @GetMapping()
    public String getAllConnection(Model model){
        model.addAttribute("connections",connectionService.getAllConnection());
        return "user/connection/index";
    }

    @GetMapping("create")
    public String createConnection(Model model){
        model.addAttribute("sources",sourceService.getAllSources());
        model.addAttribute("destinations",destinationService.getAllDestinations());
        return "user/connection/create";
    }

    @PostMapping("store")
    public String createConnection(@ModelAttribute("connection") Connection connection){
        connectionService.createConnection(connection);
        return "redirect:/user/connection";
    }
}
