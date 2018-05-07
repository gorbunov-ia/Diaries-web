package ru.gorbunov.diaries.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for home page.
 *
 * @author Gorbunov.ia
 */
@Controller
public class HomeController {

    /**
     * Redirect to home page.
     *
     * @return home page
     */
    @GetMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }

}
