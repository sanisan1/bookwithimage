package com.example.gradle_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping
    public String tsestApi() {
        return "РАБОТАЕТ";
    }
}
