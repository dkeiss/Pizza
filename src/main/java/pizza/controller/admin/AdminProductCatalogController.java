package pizza.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pizza.service.AdditionalService;
import pizza.service.AdminProductCatalogService;
import pizza.service.ProductCatalogService;
import pizza.vo.product.menu.ProductCatalogFullVO;
import pizza.vo.product.menu.ProductCatalogInfoVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Created by Daniel Keiss on 06.11.2016.
 */
@Controller
@RequestMapping("rest/admin/productcatalog")
public class AdminProductCatalogController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AdminProductCatalogService adminProductCatalogService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    ProductCatalogInfoVO uploadProductCatalog(@RequestParam("file") MultipartFile file) throws IOException {
        String fileString = read(file.getInputStream());
        ProductCatalogFullVO productCatalogFull = objectMapper.readValue(fileString, ProductCatalogFullVO.class);
        return adminProductCatalogService.createProductCatalogFull(productCatalogFull);
    }

    private static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

}
