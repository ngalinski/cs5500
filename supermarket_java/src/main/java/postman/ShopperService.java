package postman;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ShopperService {

    @Autowired
    private ShopperRepository repo;

    public List<Shopper> listAll() {
        return repo.findAll();
    }

    public void save(Shopper shopper) {
        repo.save(shopper);
    }

    public Shopper get(Integer id) {
        return repo.findById(id).get();
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
