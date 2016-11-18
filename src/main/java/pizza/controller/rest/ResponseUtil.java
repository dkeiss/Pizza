package pizza.controller.rest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel Keiss on 05.11.2016.
 */
public class ResponseUtil {

    public static Map<String, Object> getEmptyJsonSucessResponse(){
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

}
