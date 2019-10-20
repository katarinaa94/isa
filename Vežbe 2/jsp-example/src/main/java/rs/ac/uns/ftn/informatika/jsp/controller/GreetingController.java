package rs.ac.uns.ftn.informatika.jsp.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import rs.ac.uns.ftn.informatika.jsp.domain.Greeting;
import rs.ac.uns.ftn.informatika.jsp.service.GreetingService;

@Controller
/*
 * Mapiranje zahteva moze se obaviti na nekoliko nacina. Jedan od nacina je
 * koriscenjem @RequestMapping anotacije. Anotacija moze ici na nivou klase
 * (kontrolera) gde se zahtevi za pojedinim resursima mogu grupisati prema
 * klasama.
 */
@RequestMapping("/greetings")
public class GreetingController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GreetingService greetingService;

	/*
	 * GET zahtevi se mogu specificirati anotacijom @GetMapping ili anotacijom
	 * 
	 * @RequestMapping(value = "/neki_url", method = RequestMethod.GET) ModelAndView
	 * je klasa koja sadrzi informacije o view-u koji treba da se vrati korisniku,
	 * kao i modelu podataka koji se vraca.
	 */
	@GetMapping
	public ModelAndView getGreetings() {
		logger.info("> getGreetings");

		Collection<Greeting> greetings = greetingService.findAll();

		logger.info("< getGreetings");
		return new ModelAndView("listGreetings", "greetings", greetings);
	}

	/*
	 * Alternativa vracanju ModelAndView objekta je da se kao parametar metode
	 * navede objekat tipa Model koji ce sadrzati podatke koji se salju view-u, a da
	 * se kao povratna vrednost navede samo naziv view-a na koji se vrsi
	 * redirekcija.
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String getNew(Model model) {
		model.addAttribute("greeting", new Greeting());
		return "createGreeting";
	}

	/*
	 * POST zahtevi se mogu specificirati anotacijom @PostMapping ili anotacijom
	 * 
	 * @RequestMapping(value = "/neki_url", method = RequestMethod.POST) U primeru
	 * je prikazano kako se objekat koji se prosledjuje kao parametar metode
	 * createGreeting moze validirati u skladu sa anotacijama koje su navedene u
	 * samom modelu, tj. klasi Greeting. Prvo, navodjenjem parametra greeting
	 * pokusace se sa uvezivanjem delova forme u objekat koji odgovara atributu
	 * modelAttribute iz forme sa stranice createGreeting.jsp. Ukoliko dodje do
	 * greske prilikom uvezivanja, informacije o tome ce se naci u objektu tipa
	 * BindingResult iz kojeg se mogu ocitati i poslati nazad na originalnu JSP
	 * stranicu. Navodjenjem anotacije @Valid ispred objekta proveravace se da li
	 * svaki atribut zadovoljava ogranicenja navedena anotacijama (npr. @NotEmpty).
	 */
	@PostMapping(value = "/create")
	public ModelAndView createGreeting(@Valid Greeting greeting, BindingResult result) throws Exception {
		logger.info("> createGreeting");
		if (result.hasErrors()) {
			return new ModelAndView("createGreeting", "formErrors", result.getAllErrors());
		}
		greetingService.create(greeting);
		logger.info("< createGreeting");
		return new ModelAndView("redirect:/greetings", "greetings", greetingService.findAll());
	}

	@PostMapping(value = "/update")
	public ModelAndView updateGreeting(@Valid Greeting greeting, BindingResult result) throws Exception {
		logger.info("> updateGreeting");
		if (result.hasErrors()) {
			return new ModelAndView("updateGreeting", "formErrors", result.getAllErrors());
		}
		greetingService.update(greeting);
		logger.info("< updateGreeting");
		return new ModelAndView("redirect:/greetings", "greetings", greetingService.findAll());
	}

	/*
	 * Primer ocitavanja promenljivog dela url-a. Promenljivi deo se navodi u
	 * viticastim zagradama {} i ocitava kao parametar metode
	 * navodjenjem @PathVariable anotacije ispred promenljive kako bi se izvrsilo
	 * vezivanje.
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String getUpdate(@PathVariable Long id, Model model) {
		logger.info("> updateGreeting id:{}", id);
		model.addAttribute("greeting", greetingService.findOne(id));
		logger.info("< updateGreeting id:{}", id);
		return "updateGreeting";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteGreeting(@PathVariable Long id) {
		logger.info("> deleteGreeting id:{}", id);
		greetingService.delete(id);
		logger.info("< deleteGreeting id:{}", id);
		return "redirect:..";
	}

	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Exception u kontroleru");
	}

}