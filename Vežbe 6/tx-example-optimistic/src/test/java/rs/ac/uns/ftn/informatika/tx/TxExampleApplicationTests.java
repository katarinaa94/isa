package rs.ac.uns.ftn.informatika.tx;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import rs.ac.uns.ftn.informatika.tx.domain.Product;
import rs.ac.uns.ftn.informatika.tx.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TxExampleApplicationTests {
	
	@Autowired
	private ProductService productService;

	@Before
	public void setUp() throws Exception {
		productService.save(new Product("P1", "O1", 5L));
		productService.save(new Product("P2","O2", 4L));
		productService.save(new Product("P3","O3", 3L));
		productService.save(new Product("P4","O4", 1L));
		productService.save(new Product("P5","O4", 1L));
	}

	@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void testOptimisticLockingScenario() {

		Product productForUserOne = productService.findById(1L);
		Product productForUserTwo = productService.findById(1L);

		//modifikovanje istog objekta
		productForUserOne.setPrice(800L);
		productForUserTwo.setPrice(900L);

		//verzija oba objekta je 0
		assertEquals(0, productForUserOne.getVersion().intValue());
		assertEquals(0, productForUserTwo.getVersion().intValue());

		//pokusaj cuvanja prvog objekta
		productService.save(productForUserOne);

		//pokusaj cuvanja drugog objekta - Exception!
		productService.save(productForUserTwo);
	}

}
