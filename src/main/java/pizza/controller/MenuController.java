package pizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pizza.domain.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;

/**
 * Created by Daniel Keiss on 23.09.2016.
 */
@Controller
public class MenuController {

    @RequestMapping(value = "/ResponseFoodMenu", method = RequestMethod.POST)
    public
    @ResponseBody
    String menu(Principal principal, Model model) throws IOException {
        return new String(Files.readAllBytes(Paths.get("Speisekarte.json")));
    }

}