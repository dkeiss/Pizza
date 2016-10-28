package pizza.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import pizza.vo.product.additional.AdditionalMenusVO;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Daniel Keiss on 26.09.2016.
 */
public class OldMenuControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getAdditionalInfo() throws Exception {
        String additionalMenuJson = new String(Files.readAllBytes(Paths.get("documentation/ZusatzMenu.json")));
        AdditionalMenusVO additionalMenusVO = objectMapper.readValue(additionalMenuJson, AdditionalMenusVO.class);
        System.out.println(additionalMenusVO);
    }

}