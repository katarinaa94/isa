package rs.ac.uns.ftn.informatika.validation.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.validation.domain.Greeting;
import rs.ac.uns.ftn.informatika.validation.repository.InMemoryGreetingRepository;

@Service
public class GreetingServiceImpl implements GreetingService {

	@Autowired
	private InMemoryGreetingRepository greetingRepository;

	@Override
	public Collection<Greeting> findAll() {
		Collection<Greeting> greetings = greetingRepository.findAll();
		return greetings;
	}

	@Override
	public Greeting findOne(Long id) {
		Greeting greeting = greetingRepository.findOne(id);
		return greeting;
	}

	@Override
	public Greeting create(Greeting greeting) throws Exception {
		if (greeting.getId() != null) {
			throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
		}
		Greeting savedGreeting = greetingRepository.create(greeting);
		return savedGreeting;
	}

	@Override
	public Greeting update(Greeting greeting) throws Exception {
		Greeting greetingToUpdate = findOne(greeting.getId());
		if (greetingToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		greetingToUpdate.setText(greeting.getText());
		Greeting updatedGreeting = greetingRepository.create(greetingToUpdate);
		return updatedGreeting;
	}

	@Override
	public void delete(Long id) {
		greetingRepository.delete(id);
	}

}
