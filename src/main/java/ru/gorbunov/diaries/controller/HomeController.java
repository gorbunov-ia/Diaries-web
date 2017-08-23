package ru.gorbunov.diaries.controller;

import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Gorbunov.ia
 */
@Controller
@RequestMapping(path = "/")
public class HomeController {
    
    private final Logger log = LoggerFactory.getLogger(HomeController.class); 
    
    @ModelAttribute("module")
    public String module() {
        return "home";
    }

    @GetMapping("/")
    public String getPage() {
        return "home";
    }    
    
}
