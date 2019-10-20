package rs.ac.uns.ftn.informatika.jsp.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.jsp.domain.Greeting;
import rs.ac.uns.ftn.informatika.jsp.repository.InMemoryGreetingRepository;

@Service
public class GreetingServiceImpl implements GreetingService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InMemoryGreetingRepository greetingRepository;

	@Override
	public Collection<Greeting> findAll() {
		logger.info("> findAll");
		Collection<Greeting> greetings = greetingRepository.findAll();
		logger.info("< findAll");
		return greetings;
	}

	@Override
	public Greeting findOne(Long id) {
		logger.info("> findOne id:{}", id);
		Greeting greeting = greetingRepository.findOne(id);
		logger.info("< findOne id:{}", id);
		return greeting;
	}

	@Override
	public Greeting create(Greeting greeting) throws Exception {
		logger.info("> create");
		if (greeting.getId() != null) {
			logger.error("Pokusaj kreiranja novog entiteta, ali Id nije null.");
			throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
		}
		Greeting savedGreeting = greetingRepository.create(greeting);
		logger.info("< create");
		return savedGreeting;
	}

	@Override
	public Greeting update(Greeting greeting) throws Exception {
		logger.info("> update id:{}", greeting.getId());
		Greeting greetingToUpdate = findOne(greeting.getId());
		if (greetingToUpdate == null) {
			logger.error("Pokusaj azuriranja entiteta, ali je on nepostojeci.");
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		greetingToUpdate.setText(greeting.getText());
		Greeting updatedGreeting = greetingRepository.create(greetingToUpdate);
		logger.info("< update id:{}", greeting.getId());
		return updatedGreeting;
	}

	@Override
	public void delete(Long id) {
		logger.info("> delete id:{}", id);
		greetingRepository.delete(id);
		logger.info("< delete id:{}", id);
	}

}
