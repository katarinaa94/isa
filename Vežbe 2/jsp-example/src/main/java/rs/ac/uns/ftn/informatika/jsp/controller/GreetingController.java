package rs.ac.uns.ftn.informatika.jsp.controller;

import java.util.Collection;

import javax.validation.Valid;

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
		Collection<Greeting> greetings = greetingService.findAll();
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
		if (result.hasErrors()) {
			return new ModelAndView("createGreeting", "formErrors", result.getAllErrors());
		}
		
		greetingService.create(greeting);
		return new ModelAndView("redirect:/greetings", "greetings", greetingService.findAll());
	}

	@PostMapping(value = "/update")
	public ModelAndView updateGreeting(@Valid Greeting greeting, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return new ModelAndView("updateGreeting", "formErrors", result.getAllErrors());
		}
		
		greetingService.update(greeting);
		return new ModelAndView("redirect:/greetings", "greetings", greetingService.findAll());
	}

	/*
	 * Primer ocitavanja promenljivog dela url-a. Promenljivi deo se navodi u
	 * viticastim zagradama {} i ocitava kao parametar metode
	 * navodjenjem @PathVariable anotacije ispred promenljive kako bi se izvrsilo
	 * vezivanje.
	 */
	@GetMapping(value = "/update/{id}")
	public String getUpdate(@PathVariable Long id, Model model) {
		model.addAttribute("greeting", greetingService.findOne(id));
		return "updateGreeting";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteGreeting(@PathVariable Long id) {
		greetingService.delete(id);
		return "redirect:..";
	}

	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Exception u kontroleru");
	}

}