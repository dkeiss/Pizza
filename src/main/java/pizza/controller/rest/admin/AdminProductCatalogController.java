package pizza.controller.rest.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
public class AdminProductCatalogController implements ResourceLoaderAware {

    @Autowired
    private AdminProductCatalogService adminProductCatalogService;

    private ResourceLoader resourceLoader;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    ProductCatalogInfoVO uploadProductCatalog(@RequestParam("file") MultipartFile file) throws IOException {
        ProductCatalogFullVO productCatalogFull = adminProductCatalogService.getProductCatalogFullVO(file);
        return adminProductCatalogService.createProductCatalogFull(productCatalogFull);
    }

    @RequestMapping(value = "/download/{fileType}", method = RequestMethod.GET)
    public HttpEntity<InputStreamResource> getFile(@PathVariable("fileType") String fileType, HttpServletResponse response) throws FileNotFoundException {
        InputStream inputStream = getInputStream("/static/json/product_catalog_" + fileType.toLowerCase() + ".json");

        try {
            return getInputStreamResourceHttpEntity("attachment; filename=" + fileType + ".json", inputStream.available(), inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/download-manual", method = RequestMethod.GET)
    public HttpEntity<InputStreamResource> getManual(HttpServletResponse response) throws FileNotFoundException {
        InputStream inputStream = getInputStream("/static/pdf/Handbuch.pdf");

        try {
            return getInputStreamResourceHttpEntity("attachment; filename=Handbuch.pdf", inputStream.available(), inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getInputStream(String location) {
        Resource resource = getResource("classpath:" + location);
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (inputStream == null) {
            throw new NotFoundException();
        }
        return inputStream;
    }

    private HttpEntity<InputStreamResource> getInputStreamResourceHttpEntity(String headerValue, long length, InputStream inputStream) throws FileNotFoundException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json"));
        header.set("Content-Disposition",
                headerValue);
        header.setContentLength(length);

        InputStreamResource isr = new InputStreamResource(inputStream);
        return new ResponseEntity<>(isr, header, HttpStatus.OK);
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Resource getResource(String location) {
        return resourceLoader.getResource(location);
    }

}
