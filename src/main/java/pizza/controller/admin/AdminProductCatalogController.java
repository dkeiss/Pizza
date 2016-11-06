package pizza.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pizza.service.AdminProductCatalogService;
import pizza.service.exception.NotFoundException;
import pizza.vo.product.menu.ProductCatalogFullVO;
import pizza.vo.product.menu.ProductCatalogInfoVO;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @RequestMapping(value = "/download/{fileType}", method = RequestMethod.GET)
    public HttpEntity<InputStreamResource> getFile(@PathVariable("fileType") String fileType, HttpServletResponse response) throws FileNotFoundException {
        Path path = Paths.get("src/main/resources/static/json/product_catalog_" + fileType.toLowerCase() + ".json");
        File file = path.toFile();
        if (!file.exists()) {
            throw new NotFoundException();
        }

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json"));
        header.set("Content-Disposition",
                "attachment; filename=" + fileType + ".json");
        header.setContentLength(file.length());

        InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
        return new ResponseEntity<>(isr, header, HttpStatus.OK);
    }

    private static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

}
