package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.clusterpi.model.Product;

@RestController
public class HelloController {

    private static final String template = "Hello, %s!";

    @RequestMapping("/GetPomme")
    public Product getPomme(@RequestParam(value="name", defaultValue="rototo") String name) {
        return new Product(,
                          ,
                          ,
                          ,
                          );
    }
}
