package pizza.controller.rest.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pizza.service.AdminProductCatalogService;
import pizza.service.exception.NotFoundException;
import pizza.service.exception.productCatalog.ProductCatalogInvalidException;
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

    @Autowired
    private AdminProductCatalogService adminProductCatalogService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    ProductCatalogInfoVO uploadProductCatalog(@RequestParam("file") MultipartFile file) throws IOException {
        ProductCatalogFullVO productCatalogFull = adminProductCatalogService.getProductCatalogFullVO(file);
        return adminProductCatalogService.createProductCatalogFull(productCatalogFull);
    }

    @RequestMapping(value = "/download/{fileType}", method = RequestMethod.GET)
    public HttpEntity<InputStreamResource> getFile(@PathVariable("fileType") String fileType, HttpServletResponse response) throws FileNotFoundException {
        Path path = Paths.get("src/main/resources/static/json/product_catalog_" + fileType.toLowerCase() + ".json");
        File file = path.toFile();
        if (!file.exists()) {
            throw new NotFoundException();
        }

        return getInputStreamResourceHttpEntity("attachment; filename=" + fileType + ".json", file.length(), new FileInputStream(file));
    }

    @RequestMapping(value = "/download-manual", method = RequestMethod.GET)
    public HttpEntity<InputStreamResource> getManual(HttpServletResponse response) throws FileNotFoundException {
        Path path = Paths.get("src/main/resources/static/pdf/Handbuch.pdf");
        File file = path.toFile();
        if (!file.exists()) {
            throw new NotFoundException();
        }

        return getInputStreamResourceHttpEntity("attachment; filename=Handbuch.pdf", file.length(), new FileInputStream(file));
    }

    private HttpEntity<InputStreamResource> getInputStreamResourceHttpEntity(String headerValue, long length, FileInputStream inputStream) throws FileNotFoundException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json"));
        header.set("Content-Disposition",
                headerValue);
        header.setContentLength(length);

        InputStreamResource isr = new InputStreamResource(inputStream);
        return new ResponseEntity<>(isr, header, HttpStatus.OK);
    }

}
