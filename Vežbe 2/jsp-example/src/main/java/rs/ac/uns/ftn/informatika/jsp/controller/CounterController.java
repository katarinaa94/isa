package rs.ac.uns.ftn.informatika.jsp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import rs.ac.uns.ftn.informatika.jsp.domain.Counter;

@Controller
@RequestMapping("/counter")
/*
 * Jedan od nacina da se iskoristi Http sesija sa Spring web aplikacijom je putem
 * anotacija @SessionAttributes gde se navode objekti cije stanje se cuva. Alternativni nacini
 * su da se navodi HttpSession objekat kao parametar metode ili da se injektuje HttpSession objekat kao
 * atribut klase.
 */
@SessionAttributes({"counter"})
public class CounterController {
	
	private static Log log = LogFactory.getLog(CounterController.class);
	
	/*
	 * ModelMap je treci nacin za upravljanje objektima
	 * koji se prosledjuju view-u.
	 */
	@RequestMapping
	public String getCounter(ModelMap model) {
		
		Counter counter = (Counter)model.get("counter");
		if(counter == null)
			counter = new Counter();
		
		model.addAttribute("counter", counter);
		
		counter.inc();
		log.info("Vrednost brojaca je " + counter.getCount());
		
		return "counter";
	}

}
