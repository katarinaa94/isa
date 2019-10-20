package rs.ac.uns.ftn.informatika.validation.repository;

import java.util.Collection;

import rs.ac.uns.ftn.informatika.validation.domain.Greeting;

public interface GreetingRepository {

	Collection<Greeting> findAll();

	Greeting create(Greeting greeting);

	Greeting findOne(Long id);

	Greeting update(Greeting greeting);

	void delete(Long id);

}
