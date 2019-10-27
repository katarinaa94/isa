package rs.ac.uns.ftn.informatika.jsp.repository;

import java.util.Collection;

import rs.ac.uns.ftn.informatika.jsp.domain.Greeting;

public interface GreetingRepository {

	Collection<Greeting> findAll();

	Greeting create(Greeting greeting);

	Greeting findOne(Long id);
	
	Greeting update(Greeting greeting);

	void delete(Long id);

}
