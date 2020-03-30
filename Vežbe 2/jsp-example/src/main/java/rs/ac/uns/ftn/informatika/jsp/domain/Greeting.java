package rs.ac.uns.ftn.informatika.jsp.domain;

import javax.validation.constraints.NotEmpty;

public class Greeting {
	
    private Long id;
    
    /*
     * Jedna u nizu anotacija za validaciju polja sa serverske strane.
     * Prema dokumentaciji: The annotated element must not be null nor empty. Supported types are:
	     * CharSequence (length of character sequence is evaluated)
	     * Collection (collection size is evaluated)
	     * Map (map size is evaluated)
	     * Array (array length is evaluated)
     */
    @NotEmpty(message = "Poruka je obavezna.")
    private String text;

    public Greeting() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
