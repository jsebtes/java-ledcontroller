package fr.jstessier.ledcontroller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dev on 02/07/16.
 */
@Controller
public class RedirectionController {

    @RequestMapping({
            "/leds",
            "/leds/*",
            "/groups",
            "/groups/*"
    })
    public String index() {
        return "forward:/index.html";
    }

}
