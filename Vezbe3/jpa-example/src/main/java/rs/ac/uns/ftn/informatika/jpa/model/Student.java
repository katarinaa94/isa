package rs.ac.uns.ftn.informatika.jpa.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/*
 * @Entity anotacija naznacava da je klasa perzistentni entitet. Klasa ima konstruktor bez parametara.
 * Dodatno se moze iskoristiti anotacija @Table("naziv_tabele_u_bazi") kojom se
 * specificira tacan naziv tabele u bazi, sema kojoj pripada, itd. Ako se izostavi ova anotacija, dovoljno je
 * imati anotaciju @Entity i u bazi ce se kreirati tabela sa nazivom klase.
 */
@Entity
public class Student {

	/*
	 * Svaki entitet ima svoj kljuc (surogat kljuc), dok se strategija generisanja
	 * kljuceva moze eksplicitno podesiti: - AUTO - generisanje kljuceva se oslanja
	 * na perzistencionog provajdera da izabere nacin generisanja (ako je u pitanju
	 * Hibernate, selektuje tip na osnovu dijalekta baze, za najpopularnije baze
	 * izabrace IDENTITY) - IDENTITY - inkrementalno generisanje kljuceva pri svakom
	 * novom insertu u bazu - SEQUENCE - koriste se specijalni objekti baze da se
	 * generisu id-evi - TABLE - postoji posebna tabela koja vodi racuna o
	 * kljucevima svake tabele Vise informacija na:
	 * https://en.wikibooks.org/wiki/Java_Persistence/Identity_and_Sequencing
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*
	 * Kolona moze imati ime koje se razlikuje od naziva atributa.
	 */
	@Column(name = "indexNumber", unique = true, nullable = false)
	String index;

	/*
	 * Anotacija @Column oznacava da ce neki atribut biti kolona u tabeli
	 */
	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "lastName", nullable = false)
	private String lastName;

	/*
	 * Primer bidirekcione veze 1:n. Student sadrzi kolekciju ispita, ispit pripada
	 * jednom student. Jedna strana veze je anotirana sa @OneToMany, dok je druga
	 * anotirana sa @ManyToOne. Dodatno je iskoriscen atribut mappedBy da se naznaci
	 * ko je vlasnik veze (student). U bazi ce se u tabeli Exam kreirati dodatna
	 * kolona koja ce sadrzati id objekata tipa Student kao strani kljuc. Ako se
	 * izostavi mappedBy kreirace se medjutabela koja ce sadrzati 2 kolone - id
	 * studenta i id ispita
	 * 
	 * Atributom fetch moze se podesavati nacin dobavljanja povezanih entiteta.
	 * Opcije su EAGER i LAZY. FetchType odlucuje da li ce se ucitati i sve veze sa
	 * odgovarajucim objektom cim se inicijalno ucita sam objekat ili nece. Ako je
	 * FetchType EAGER ucitace se sve veze sa objektom odmah, a ako je FetchType
	 * LAZY ucitace se tek pri eksplicitnom trazenju povezanih objekata (pozivanjem
	 * npr. metode getExams). Vise informacija na:
	 * https://howtoprogramwithjava.com/hibernate-eager-vs-lazy-fetch-type/
	 * 
	 * Pored atributa fetch moze se iskoristiti i atribut cascade. CascadeType
	 * podesen na All dozvoljava da se prilikom svakog cuvanja, izmene ili brisanja
	 * studenta cuvaju, menjaju ili brisu i ispiti. To znaci da ne moraju unapred da
	 * se cuvaju ispiti pa onda povezuju sa studentom. orphanRemoval podesen na true
	 * ce obezbediti da se ispiti izbrisu iz baze kada se izbrisu iz kolekcije
	 * ispita u objektu student.
	 */
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Exam> exams = new HashSet<Exam>();

	public Student() {
		super();
	}

	public Student(Long id, String index, String firstName, String lastName) {
		super();
		this.id = id;
		this.index = index;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Exam> getExams() {
		return exams;
	}

	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Student s = (Student) o;
		if (s.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, s.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", index=" + index + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", exams=" + exams + "]";
	}
}
