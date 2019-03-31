package com.moebius.frontend.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
}
