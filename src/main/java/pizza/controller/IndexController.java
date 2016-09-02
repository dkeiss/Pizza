package pizza.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.security.Principal;
import java.util.Properties;

/**
 * Created by Daniel Keiss on 02.09.2016.
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(Principal principal) {
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Principal principal) {
        return "index";
    }

    @RequestMapping("properties")
    @ResponseBody
    Properties properties() {
        return System.getProperties();
    }

}
