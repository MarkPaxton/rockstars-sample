package rockstars.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {
    @RequestMapping("/")
    public String index() {
        return "OK";
    }
}
