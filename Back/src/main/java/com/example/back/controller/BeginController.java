package com.example.back.controller;

import com.example.back.message.Message;
import com.example.back.service.BeginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/begin")
public class BeginController {
    @Autowired
    BeginService beginService;


    @PostMapping("/login")
    @ResponseBody
    public Message login() {
        return beginService.begin();
    }

    @PostMapping("/run")
    @ResponseBody
    public Message run() {
        return beginService.run();
    }

    @PostMapping("/stop")
    @ResponseBody
    public Message stop() {
        return beginService.stop();
    }

    @PostMapping("/over")
    @ResponseBody
    public Message over() {
        return beginService.over();
    }

    @PostMapping("/next")
    @ResponseBody
    public Message next() {
        return beginService.next();
    }
}