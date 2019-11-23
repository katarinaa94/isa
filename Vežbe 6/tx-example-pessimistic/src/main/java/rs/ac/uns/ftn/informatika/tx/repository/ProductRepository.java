package rs.ac.uns.ftn.informatika.tx.repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import rs.ac.uns.ftn.informatika.tx.domain.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
	
	//Zakljucavamo product koji se vraca za citanje i pisanje
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select p from Product p where p.id = :id")
	//Postgres po defaultu poziva for update bez no wait, tako da treba dodati vrednost 0 za timeout
	//kako bismo dobili PessimisticLockingFailureException ako pri pozivu ove metode torka nije dostupna
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	public Product findOneById(Long id);

}
