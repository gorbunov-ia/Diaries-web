package ru.gorbunov.diaries.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Test.
 */
@RestController
public class GuideController {

    /**
     * From guide.
     *
     * @return mock
     */
    @RequestMapping("/resource")
    public Map<String, Object> home() {
        Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }

    /**
     * Test.
     *
     * @param user usr
     * @return usr
     */
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }


}
