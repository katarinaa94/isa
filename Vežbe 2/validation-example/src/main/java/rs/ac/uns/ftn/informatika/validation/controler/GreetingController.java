package rs.ac.uns.ftn.informatika.validation.controler;

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
import org.springframework.web.servlet.ModelAndView;

import rs.ac.uns.ftn.informatika.validation.domain.Greeting;
import rs.ac.uns.ftn.informatika.validation.service.GreetingService;

@Controller
@RequestMapping("/greetings")
public class GreetingController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GreetingService greetingService;

	@GetMapping
	public ModelAndView getGreetings() {
		logger.info("> getGreetings");

		Collection<Greeting> greetings = greetingService.findAll();

		logger.info("< getGreetings");
		return new ModelAndView("listGreetings", "greetings", greetings);
	}

	@GetMapping(value = "/new")
	public String getNew(Model model) {
		model.addAttribute("greeting", new Greeting());
		return "createGreeting";
	}

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

	@GetMapping(value = "/update/{id}")
	public String getUpdate(@PathVariable Long id, Model model) {
		logger.info("> updateGreeting id:{}", id);
		model.addAttribute("greeting", greetingService.findOne(id));
		logger.info("< updateGreeting id:{}", id);
		return "updateGreeting";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteGreeting(@PathVariable Long id) {
		logger.info("> deleteGreeting id:{}", id);
		greetingService.delete(id);
		logger.info("< deleteGreeting id:{}", id);
		return "redirect:..";
	}

	@GetMapping("foo")
	public String foo() {
		throw new RuntimeException("Exception u kontroleru");
	}

}