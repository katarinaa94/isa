package rs.ac.uns.ftn.informatika.tx.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.informatika.tx.domain.Product;
import rs.ac.uns.ftn.informatika.tx.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = false)
	public Product save(Product product) {
		logger.info("> create");
		Product savedProduct = productRepository.save(product);
		logger.info("< create");
		return savedProduct;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(long id) {
		
		logger.info("> delete");
		productRepository.deleteById(id);
		logger.info("< delete");
	}

	public Product findById(long id) {
		
		logger.info("> findById id:{}", id);
		Product product = productRepository.findById(id).get();
		logger.info("< findById id:{}", id);
		return product;
	}
	
	public List<Product> findAll() {
		
		logger.info("> findAll");
		List<Product> products = productRepository.findAll();
		logger.info("< findAll");
		return products;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Product update(Product product) {

		logger.info("> update id:{}", product.getId());
		Product productToUpdate = productRepository.findOneById(product.getId());
		//Uspavljujemo thread kako bi se simlirala duza operacija zarad demonstracije locka
		try { Thread.sleep(7000); } catch (InterruptedException e) { }
		productToUpdate.setName(product.getName());
		productToUpdate.setOrigin(product.getOrigin());
		productToUpdate.setPrice(product.getPrice());
		productRepository.save(productToUpdate);
		logger.info(productToUpdate.toString());
		logger.info("< update id:{}", product.getId());

		return productToUpdate;
	}


}
