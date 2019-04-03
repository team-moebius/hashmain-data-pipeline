package com.moebius.frontend.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }


}
