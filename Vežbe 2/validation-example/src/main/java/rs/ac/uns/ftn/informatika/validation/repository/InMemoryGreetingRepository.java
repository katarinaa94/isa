package rs.ac.uns.ftn.informatika.validation.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import rs.ac.uns.ftn.informatika.validation.domain.Greeting;

@Repository
public class InMemoryGreetingRepository implements GreetingRepository {

	private static AtomicLong counter = new AtomicLong();

	private final ConcurrentMap<Long, Greeting> greetings = new ConcurrentHashMap<Long, Greeting>();

	@Override
	public Collection<Greeting> findAll() {
		return this.greetings.values();
	}

	@Override
	public Greeting create(Greeting greeting) {
		Long id = greeting.getId();
		if (id == null) {
			id = counter.incrementAndGet();
			greeting.setId(id);
		}
		this.greetings.put(id, greeting);
		return greeting;
	}

	@Override
	public Greeting findOne(Long id) {
		return this.greetings.get(id);
	}

	@Override
	public void delete(Long id) {
		this.greetings.remove(id);
	}

	@Override
	public Greeting update(Greeting greeting) {
		Long id = greeting.getId();
		if (id != null) {
			this.greetings.put(id, greeting);
		}
		return greeting;
	}

}
