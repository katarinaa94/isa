package rs.ac.uns.ftn.informatika.validation.controler;

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
import org.springframework.web.servlet.ModelAndView;

import rs.ac.uns.ftn.informatika.validation.domain.Greeting;
import rs.ac.uns.ftn.informatika.validation.service.GreetingService;

@Controller
@RequestMapping("/greetings")
public class GreetingController {

	@Autowired
	private GreetingService greetingService;

	@GetMapping
	public ModelAndView getGreetings() {
		Collection<Greeting> greetings = greetingService.findAll();
		return new ModelAndView("listGreetings", "greetings", greetings);
	}

	@GetMapping(value = "/new")
	public String getNew(Model model) {
		model.addAttribute("greeting", new Greeting());
		return "createGreeting";
	}

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

	@GetMapping("foo")
	public String foo() {
		throw new RuntimeException("Exception u kontroleru");
	}

}