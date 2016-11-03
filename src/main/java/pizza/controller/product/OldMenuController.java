package pizza.controller.product;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pizza.vo.product.additional.old.AdditionalMenuVO;
import pizza.vo.product.additional.old.AdditionalMenusVO;
import pizza.vo.product.additional.old.GetAdditionalMenuVO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

/**
 * Created by Daniel Keiss on 23.09.2016.
 */
@Controller
public class OldMenuController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/ResponseFoodMenu", method = RequestMethod.POST)
    public
    @ResponseBody
    String responseFoodMenu(Principal principal, Model model) throws IOException {
        return new String(Files.readAllBytes(Paths.get("documentation/Speisekarte.json")));
    }

    @RequestMapping(value = "/GetAdditionalInfo", method = RequestMethod.POST)
    public
    @ResponseBody
    AdditionalMenuVO getAdditionalInfo(@RequestBody GetAdditionalMenuVO getAdditionalMenuVO, Principal principal, Model model) throws IOException, JsonMappingException {
        String additionalMenuJson = new String(Files.readAllBytes(Paths.get("documentation/ZusatzMenu.json")));
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