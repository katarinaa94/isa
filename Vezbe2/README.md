# Vežbe 2

## aop-example

Primer Spring aplikacije u kojoj je definisan jedan aspekt (__TimeLoggingAspect__).

Podrška za aspekte je automatski uključena u Spring Boot aplikacije putem _@SpringBootApplication_ anotacije.  U slučaju da se nije koristio Spring Boot, podrška za aspekte bi se mogla uključiti dodavanjem anotacije _@EnableAspectJAutoProxy_ na konfiguracionu klasu ili _<aop:aspectj-autoproxy/>_ u slučaju XML konfiguracije.

###### Materijali koje je neophodno proučiti da bi se primer mogao uspešno ispratiti:

* [Aspect-oriented programming](https://www.youtube.com/watch?v=3KKUP7-o3ps)
* [AOP.pdf](https://github.com/katarinaa94/isa/blob/master/Vezbe2/AOP.pdf) iz foldera _Vezbe2_

###### Definisanje aspekta

Da bi se definisao aspekt, potrebno je Java klasu anotirati ___@Aspect___ anotacijom. Zatim svaku metodu ove klase anotirati anotacijom koja će opisati u kom trenutku će se aspekt izvršiti, a kao atribut ove anotacije potrebno je navesti __pointcut__ izraz kojim se definiše konkretno mesto u aplikaciji na kojem će aspekt biti primenjen. Anotacije kojim se anotiraju metode su sledeće:

* ___@Before___: pre poziva metode na koju se aspekt odnosi
* ___@After___: nakon metode (bez obzira na ishod metode)
* ___@AfterReturning___: nakon uspešnog završetka metode
* ___@AfterThrowing___ : nakon što metoda izazove izuzetak
* ___@Around___: omotač oko metode, tako što se deo koda izvršava pre, a deo posle metode.

__Pointcut__ izrazom se definiše __šablon__, što znači da se aspekt primenjuje na __svaku__ metodu koja se uklapa u definisani šablon!

###### Dodatni materijali:

* [Primer još jedne Spring aplikacije sa aspektima](https://www.journaldev.com/2583/spring-aop-example-tutorial-aspect-advice-pointcut-joinpoint-annotations)
* [AOP i Spring Dokumentacija](https://docs.spring.io/spring/docs/2.0.x/reference/aop.html)
* [Specifikacija AsspectJ jezika](https://www.eclipse.org/aspectj/doc/released/progguide/language.html)

###### Pokretanje primera (Eclipse)

* importovati projekat u workspace: Import -> Maven -> Existing Maven Project
* instalirati sve dependency-je iz pom.xml
* desni klik na projekat -> Run as -> Java Application / Spring Boot app (ako je instaliran STS plugin sa Eclipse marketplace)

## schedule-example

Primer Spring aplikacije sa zakazanim taskovima koji se izvršavaju u fiksnim vremenskim intervalima. Kada se primer pokrene, u konzoli analizirati vremenske trenutke kada su se metode izvršile.

Da bi Spring mogao automatski da izvršava zakazane taskove, potrebno je konfiguracinu klasu anotirati anotacijom ___@EnableScheduling___ (u našem primeru, to je main klasa ScheduleExampleApplication.java).

Metode koje predstavljaju taskove koji se izvršavaju potrebno je anotirati sa ___@Scheduled___ i podesiti atribute na željene vrednosti. Atributi su sledeći:

* [__cron__](https://en.wikipedia.org/wiki/Cron): Logika se izvrsava u vremenskim trenucima opisanim cron sintaksom. Cron je izraz koji opisuje neki vremenski trenutak (npr. svaki dan 20:00:00, nedelja 23:59:59...). Ovaj atribut se koristi ukoliko je potrebno definisati task koji će se izvršavati u nekim vremenskim trenucima.
* __initialDelay__: Atribut čija vrednost označava koje vreme treba da prođe od trenutka kada se aplikacija startuje do trenutka kada se metoda prvi put može izvršiti. Vreme se navodi u milisekundama.
* __fixedRate__: Atribut čija vrednost označava koliko vremena treba da prođe između izvrašavanja (gleda se trenutak kada je metoda _započela_ izvršavanje). Vreme se navodi u milisekundama. Npr. ako je vrednost atributa 5000 ms znači da će se metoda izvršavati na svakih 5 sekundi od trenutka poziva (npr. ako je metoda počela sa izvršavanjem u 20:00:00, završila u 20:03:00, sledeći poziv iste metode će biti u 20:05:00).
* __fixedDelay__: Atribut čija vrednost označava koliko vremena treba da prođe između izvrašavanja (gleda se trenutak kada je metoda _završila_ izvršavanje). Vreme se navodi u milisekundama. Npr. ako je vrednost atributa 5000 ms znači da će se metoda izvršavati na svakih 5 sekundi od trenutka završetka (npr. ako je metoda počela sa izvršavanjem u 20:00:00, završila u 20:03:00, sledeći poziv iste metode će biti u 20:08:00).

Sve taskove koji su zakazani Spring će izvršavati u posebnom thread-u. Koristi se samo jedan thread za sve zadatke, pa treba biti oprezan jer u jednom trenutku samo jedan zakazani task može da se izvršava. Ukoliko neki zakazani task treba da se izvrši, a Spring već izvrašava neki raniji zakazani, task će otići na čekanje i čim se thread oslobi, preuzima se na izvršavanje.

Primer ispisa na konzoli:
```
2020-03-30 14:14:24.226  INFO 28365 --- [           main] r.a.u.f.i.s.ScheduleExampleApplication   : Started ScheduleExampleApplication in 2.171 seconds (JVM running for 2.561)
2020-03-30 14:14:30.000  INFO 28365 --- [   scheduling-1] r.a.u.f.i.s.c.GreetingContoller          : > cronJob
2020-03-30 14:14:35.002  INFO 28365 --- [   scheduling-1] r.a.u.f.i.s.c.GreetingContoller          : Procesiranje je trajalo 5 sekundi.
2020-03-30 14:14:35.005  INFO 28365 --- [   scheduling-1] r.a.u.f.i.s.c.GreetingContoller          : < cronJob
```

U uglastim zagradama je oznaka thread-a koji izvršava metodu. Vidimo da postoje 2 thread-a:

1. __main__: main thread koji startuje aplikaciju i
2. __scheduling-1__: thread koji izvršava zakazane taskove.

###### Dodatni materijali:

* Više o cron sintaksi na [linku](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html)
* [The @Scheduled Annotation in Spring](https://www.baeldung.com/spring-scheduled-tasks)
* [Spring Scheduling Tasks Guidelines](https://spring.io/guides/gs/scheduling-tasks/)
* [Task Execution and Scheduling Spring documentation](https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling)

###### Pokretanje primera (Eclipse)

* importovati projekat u workspace: Import -> Maven -> Existing Maven Project
* instalirati sve dependency-je iz pom.xml
* desni klik na projekat -> Run as -> Java Application / Spring Boot app (ako je instaliran STS plugin sa Eclipse marketplace)

## jsp-example

Primer Spring web aplikacije sa JSP stranicama. Pogledati u __pom.xml__ zavisnosti koje moraju da se uključe kako bi se renderovale JSP stranice u Spring Boot aplikaciji (pošto embedovan Tomcat core nema podršku za renderovanje). Pored standardog rasporeda fajlova i foldera u projektu, u "src/main/" potrebno je dodati __webapp__ folder u kojem će se nalaziti JSP stranice. Sva dodatna konfiguracija projekta (van samih klasa koje predstavljaju konfiguraciju) piše se u __application.properties__ fajlu koji se nalazi u src/main/resorces paketu (tu je navedena i tačna putanja do JSP stranica).

###### Materijali koje je neophodno proučiti da bi se primer mogao uspešno ispratiti:

* [Arhitekture klasičnih i savremenih web aplikacija](https://www.youtube.com/watch?v=XnEnUtSw8Rc)
* [REST.pdf](https://github.com/katarinaa94/isa/blob/master/Vezbe2/REST.pdf) iz foldera _Vezbe2_

###### Struktura primera

Paketi su organizovani tako da se različite Spring komponente nalaze u svojim odgovarajućim paketima:

* kontroleri u __controller__ paketu: uloga kontrolera u aplikaciji jeste da samo prihvataju zahteve korisnika i pozivaju odgovarajuće metode servisa
* servisi u __service__ paketu: sva logika aplikacije se piše u servisnim metodama. Servisi pozivaju odgovarajuče metode repozitorijuma
* repozitorijum u __repository__ paketu: repozitorijumi su zaduženi za komunikaciju za bazom podataka. Kako još nismo učili komunikaciju sa bazom podataka, napravljene su privremene klase koje predstavljaju repozitorijume i čuvaju podatke u kolekcijama u memoriji
* model u __domain__ ili __model__ paketu: entiteti koje postoje u sistemu.

U Springu se MVC šablon razlikuje od standardnog u tome što se ispred kontrolera koji se pišu nalazi tzv. ___Front Controller___ koji prihvata sve zahteve korisnika i prosleđuje ih konkretnim kontrolerima (tačan naziv front kontrolera je _org.springframework.web.servlet.DispatcherServlet_).

Sva mapiranja na konkretne metode u kontrolerima koje će obraditi zahteve rade se kroz anotacije:

* ___@RequestMapping___: sa specificiranjem atributa _method_ ili
* izvedenim anotacijama poput ___@GetMapping___, ___@PostMapping___, ___@PutMapping___, ___@DeleteMapping___...

Gore navedene anotacije dodatno mogu da sadrže i sledeće atribute:

* __value__:  predstavlja URL koji određuje putanju do metode
* __method__: označava tip HTTP metode (samo ukoliko se koristi _@RequestMapping_ anotacija)
* __itd.__

###### Rad sa sesijom

Primer smeštanja objekta u sesiju dat je u __CounterController.java__ klasi. Jedan od načina da se iskoristi HTTP sesija sa Spring web aplikacijom je putem anotacije ___@SessionAttributes___ gde se navode objekti čije stanje se čuva. Alternativni načini su da se navodi __HttpSession__ objekat kao parametar metode koja obrađuje zahtev ili da se injektuje __HttpSession__ objekat kao atribut klase.

Kao alternativa JSP stranicama mogu se koristiti template tehnologije kao što su _Thymeleaf_, _Freemaker_, _Velocity_...

###### Validacija

U primeru je takođe obrađena i validacija bean-ova koji stižu sa klijentske strane u metode kontrolera.

Npr. imamo sledeći potpis metode kontrolera:
```
@PostMapping(value = "/create")
    public ModelAndView createGreeting(@Valid Greeting greeting, BindingResult result) throws Exception {
        ...
    }
```

Anotacijom ___@Valid___ ispred parametra naznačavamo da vrednosti atributa tog objekta koje se šalju sa klijentske strane moraju da zadovolje sva ograničenja koja su navedena u samom modelu, odnosno u klasi __Greeting__. Ova anotacija automatski pokreće validaciju podataka. Ukoliko se naruši bar jedno ograničenje, Spring će automatski da odbije zahtev klijenta i vraća HTTP odgovor sa status kodom __400 Bad Request__.

Na nivou modela, postoji samo jedno ograničenje:
```
@NotEmpty(message = "Poruka je obavezna.")
private String text;
```
Ovo znači - ukoliko se sa klijentske strane prosledi objekat koji nema atribut _text_ (to npr. može biti neki JSON objekat {'ime':'pera'}), Spring će da odbije zahtev i vraća HTTP Response čiji je status kod 400, a poruka o grešci je _Poruka je obavezna_. Za svaku ostalu vrednost ovog atributa, zahtev bez problema prolazi do kontrolera u aplikaciju.

###### Dodatni materijali (Spring MVC):

* [Spring MVC Hello World Example](https://howtodoinjava.com/spring-mvc/spring-mvc-hello-world-example/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Spring Web MVC framework documentation](https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html)

###### Dodatni materijali (Bean Validation):

* Bean validacija na [1](https://www.baeldung.com/javax-validation), [2](https://www.baeldung.com/spring-boot-bean-validation), [3](https://www.baeldung.com/java-bean-validation-not-null-empty-blank)
* [Validating Form Input Guidelines](https://spring.io/guides/gs/validating-form-input/)
* [Bean Validation API](https://docs.jboss.org/hibernate/beanvalidation/spec/2.0/api/)

###### Pokretanje primera (Eclipse)

* importovati projekat u workspace: Import -> Maven -> Existing Maven Project
* instalirati sve dependency-je iz pom.xml
* desni klik na projekat -> Run as -> Java Application / Spring Boot app (ako je instaliran STS plugin sa Eclipse marketplace)
* automatski će se pokrenuti Tomcat i zatim odraditi deploy aplikacije - u konzoli možete ispratiti ispis
* kada se aplikacija pokrene, u browser-u navigirati URL <http://localhost:8080/myapp/greetings>

## validation-example

Primer Spring aplikacije sa Custom validacijom.

Prilikom obrade zahteva, parametar metode u kontroleru koji predstavlja objekat koji se prosleđuje serveru (a koji je anotiran ograničenjima u modelu) anotiran je sa ___@Valid___.

Greške prilikom validiranja mogu se sačuvati u objektu __BindingResult__ i vratiti klijentu za prikaz.

Pored predefinisanih anotacija za postavljanje ograničenja mogu se praviti nove anotacije. Primer jedne takve anotacije nalazi se u __validator__ paketu.

###### Dodatni materijali:

* [Bean validation Specification](https://beanvalidation.org/1.0/spec/)
* [Validation, Data Binding, and Type Conversion Documentation](https://docs.spring.io/spring/docs/4.1.x/spring-framework-reference/html/validation.html)

###### Pokretanje primera (Eclipse)

* importovati projekat u workspace: Import -> Maven -> Existing Maven Project
* instalirati sve dependency-je iz pom.xml
* desni klik na projekat -> Run as -> Java Application / Spring Boot app (ako je instaliran STS plugin sa Eclipse marketplace)

## rest-example

Primer se ne razlikuje mnogo od primera sa JSP stranicama. Jedina razlika je u kontrolerima koji su anotirani sa ___@RestController___. Anotacija je izvedena od anotacije ___@Controller___ i pridodata joj je anotacija ___@ResponseBody___ koja kao podrazumevano ponašanje kao rezultat metode koja obrađuje zahtev vraća samo telo odgovora (response body, bez zaglavlja). Za vraćanje i drugih podataka (zaglavlje, status kod,...) osim glavnog objekta koji predstavlja telo odgovora, može se koristiti klasa __ResponseEntity__.

###### Materijali koje je neophodno proučiti da bi se primer mogao uspešno ispratiti:

* [Arhitekture klasičnih i savremenih web aplikacija](https://www.youtube.com/watch?v=XnEnUtSw8Rc)
* [REST.pdf](https://github.com/katarinaa94/isa/blob/master/Vezbe2/REST.pdf) iz foldera _Vezbe2_

###### Struktura primera

Paketi su organizovani tako da se različite Spring komponente nalaze u svojim odgovarajućim paketima:

* kontroleri u __controller__ paketu: uloga kontrolera u aplikaciji jeste da samo prihvataju zahteve korisnika i pozivaju odgovarajuće metode servisa
* servisi u __service__ paketu: sva logika aplikacije se piše u servisnim metodama. Servisi pozivaju odgovarajuče metode repozitorijuma
* repozitorijum u __repository__ paketu: repozitorijumi su zaduženi za komunikaciju za bazom podataka. Kako još nismo učili komunikaciju sa bazom podataka, napravljene su privremene klase koje predstavljaju repozitorijume i čuvaju podatke u kolekcijama u memoriji
* model u __domain__ ili __model__ paketu: entiteti koje postoje u sistemu
* bilo bi dobro da postoji i dodatni paket __dto__: _**D**ata **T**ransfer **O**bjects_ su objekti koji predstavljaju skraćene verzije objekata iz modela i služe da se razmenjuju između klijentske i serverske strane. DTO objekti sadrže samo one atribute koji su u tom trenutku neophodni da se razmene između klijjenta i servera. Više o DTO objjektima će biti reči u nekim narednim primerima.

Sva mapiranja na konkretne metode u kontrolerima koje će obraditi zahteve rade se kroz anotacije:

* ___@RequestMapping___: sa specificiranjem atributa _method_ ili
* izvedenim anotacijama poput ___@GetMapping___, ___@PostMapping___, ___@PutMapping___, ___@DeleteMapping___...

Gore navedene anotacije dodatno mogu da sadrže i sledeće atribute:

* __value__:  predstavlja URL koji određuje putanju do metode
* __method__: označava tip HTTP metode (samo ukoliko se koristi _@RequestMapping_ anotacija)
* __consumes__: označava tip poruke koja se prosleđuje metodi (u kom formatu se podaci zapisuju u telu HTTP zahteva, default je JSON)
* __produces__: označava tip odgovora (u kom formatu se podaci zapisuju u telu HTTP odgovora, default je JSON).

###### Dodatni materijali:

* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Spring Restful Web Services Example](https://www.journaldev.com/2552/spring-rest-example-tutorial-spring-restful-web-services)
* [Spring REST Tutorials](https://howtodoinjava.com/spring-restful/)

###### Pokretanje primera (Eclipse)

* importovati projekat u workspace: Import -> Maven -> Existing Maven Project
* instalirati sve dependency-je iz pom.xml
* desni klik na projekat -> Run as -> Java Application / Spring Boot app (ako je instaliran STS plugin sa Eclipse marketplace)
