package rs.ac.uns.ftn.informatika.validation.domain;

import rs.ac.uns.ftn.informatika.validation.validator.CustomAnnotation;

public class Greeting {

	private Long id;

	@CustomAnnotation(message="U polje morate uneti bar tri karaktera")
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
