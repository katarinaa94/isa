package rs.ac.uns.ftn.informatika.jsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import rs.ac.uns.ftn.informatika.jsp.service.CounterService;

@Controller
@RequestMapping("/counter")
/*
 * Jedan od nacina da se iskoristi Http sesija sa Spring web aplikacijom je
 * putem anotacija @SessionAttributes gde se navode objekti cije stanje se cuva.
 * Alternativni nacini su da se navodi HttpSession objekat kao parametar metode
 * ili da se injektuje HttpSession objekat kao atribut klase.
 */
@SessionAttributes({ "counter" })
public class CounterController {
	
	@Autowired
	CounterService counterService;

	/*
	 * ModelMap je treci nacin za upravljanje objektima koji se prosledjuju view-u.
	 */
	@RequestMapping
	public String getCounter(ModelMap model) {
		counterService.incCounter(model);
		return "counter";
	}

}
