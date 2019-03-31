package com.moebius.frontend.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
