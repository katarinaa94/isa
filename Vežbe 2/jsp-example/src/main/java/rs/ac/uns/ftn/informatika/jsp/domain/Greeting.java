package rs.ac.uns.ftn.informatika.jsp.domain;

import javax.validation.constraints.NotEmpty;;

public class Greeting {

	private Long id;

	/*
	 * Jedna u nizu anotacija za validaciju polja sa serverske strane. Po
	 * dokumentaciji: Asserts that the annotated string, collection, map or array is
	 * not null or empty.
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
