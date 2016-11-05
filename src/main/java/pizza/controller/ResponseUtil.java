package pizza.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel Keiss on 05.11.2016.
 */
public class ResponseUtil {

    public static Map<String, Object> getResponseWithStatus(boolean success){
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        return response;
    }

}
