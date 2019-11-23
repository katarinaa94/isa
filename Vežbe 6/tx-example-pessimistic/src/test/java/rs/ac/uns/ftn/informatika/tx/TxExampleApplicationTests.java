package rs.ac.uns.ftn.informatika.tx;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import rs.ac.uns.ftn.informatika.tx.domain.Product;
import rs.ac.uns.ftn.informatika.tx.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TxExampleApplicationTests {

	@Autowired
	private ProductService productService;


	@Test
	public void testPessimisticLockingScenario() {
		final CountDownLatch latch = new CountDownLatch(2);
		Runnable r1 = () -> {
			Product productToUpdate = productService.findById(1L);
			productService.update(productToUpdate);
			latch.countDown();
		};

		Runnable r2 = () -> {
			try { Thread.sleep(3000); } catch (InterruptedException e) { }
			Product productToUpdate = productService.findById(1L);
			try {
				productService.update(productToUpdate);
				fail();
			}catch(Exception e) {
				assertTrue(e instanceof PessimisticLockingFailureException);
			}

			latch.countDown();
		};

		new Thread(r1).start();
		new Thread(r2).start();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
