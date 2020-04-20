package rs.ac.uns.ftn.informatika.rest.repository;

import java.util.Collection;

import rs.ac.uns.ftn.informatika.rest.domain.Greeting;

public interface GreetingRepository {

	Collection<Greeting> findAll();

	Greeting create(Greeting greeting);

	Greeting findOne(Long id);
	
	Greeting update(Greeting greeting);

	void delete(Long id);

}
