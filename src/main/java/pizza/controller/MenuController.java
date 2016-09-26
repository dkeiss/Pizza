package pizza.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pizza.domain.Order;
import pizza.vo.AdditionalMenuVO;
import pizza.vo.AdditionalMenusVO;
import pizza.vo.GetAdditionalMenuVO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by Daniel Keiss on 23.09.2016.
 */
@Controller
public class MenuController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/ResponseFoodMenu", method = RequestMethod.POST)
    public
    @ResponseBody
    String responseFoodMenu(Principal principal, Model model) throws IOException {
        return new String(Files.readAllBytes(Paths.get("Speisekarte.json")));
    }

    @RequestMapping(value = "/GetAdditionalInfo", method = RequestMethod.POST)
    public
    @ResponseBody
    AdditionalMenuVO getAdditionalInfo(@RequestBody GetAdditionalMenuVO getAdditionalMenuVO, Principal principal, Model model) throws IOException {
        String additionalMenuJson = new String(Files.readAllBytes(Paths.get("ZusatzMenu.json")));
        AdditionalMenusVO additionalMenusVO = objectMapper.readValue(additionalMenuJson, AdditionalMenusVO.class);
        List<AdditionalMenuVO> additionalMenus = additionalMenusVO.getAdditionalMenus();
        Long id = Long.valueOf(getAdditionalMenuVO.getValue());
        for (AdditionalMenuVO additionalMenu : additionalMenus) {
            if (additionalMenu.getNumbers().contains(id)) {
                return additionalMenu;
            }
        }

        return null;
    }

}