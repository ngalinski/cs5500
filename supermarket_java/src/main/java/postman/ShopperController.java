package postman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ShopperController {

    @Autowired
    private ShopperService service;

    // RESTful API methods for Retrieval operations
    @GetMapping("/products")
    public List<Shopper> list() {
        return service.listAll();
    }

    // RESTful API method for Create operation
    @GetMapping("/products/{id}")
    public ResponseEntity<Shopper> get(@PathVariable Integer id) {
        try {
            Shopper shopper = service.get(id);
            return new ResponseEntity<Shopper>(shopper, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Shopper>(HttpStatus.NOT_FOUND);
        }
    }

    // RESTful API method for Update operation
    @PostMapping("/products")
    public void add(@RequestBody Shopper shopper) {
        service.save(shopper);
    }

    // RESTful API method for Delete operation
    @PutMapping("/products/{id}")
    public ResponseEntity<?> update(@RequestBody Shopper shopper, @PathVariable Integer id) {
        try {
            Shopper existshopper = service.get(id);
            service.save(shopper);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
