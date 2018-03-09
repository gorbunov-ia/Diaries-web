package ru.gorbunov.diaries.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for home page.
 *
 * @author Gorbunov.ia
 */
@Controller
@RequestMapping(path = "/")
public class HomeController {

    /**
     * Method to get current module for template.
     *
     * @return module name
     */
    @ModelAttribute("module")
    public String module() {
        return "home";
    }

    @GetMapping("/")
    public String getPage() {
        return "home";
    }

}
