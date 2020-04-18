## cp-example

Aplikacije često moraju da koriste resurse koji su skupi za kreiranje i održavanje (npr. konekcije za pristup bazi, niti, itd).
[Resource pool](https://martinfowler.com/bliki/ResourcePool.html) pruža dobar način za upravljanje takvim resursima.

[Object Pool](https://sourcemaking.com/design_patterns/object_pool) dizajn šablon opisuje način za upravljanje resursima koji su skupi. Klijent koji želi da pristupa aplikaciji koja ima implementiran resource pool za resurse koji su skupi, treba da zatražni resurs iz pool-a umesto da zatražni kreiranje novog.
U opštem slučaju, veličina pool-a može da raste, tj. u pool će se dodavati novi objekti ukoliko je u trenutku zahteva prazan, a obično do nekog predefinisanog broja objekata koje pool može da kreira i njima upravlja.

Tipičan primer resursa koji je skup za kreiranje i korišćenje je konekcija ka bazi.
Komunikacija između aplikacije i baze podataka je sledeća:

- Sloj aplikacije koji treba da komunicira sa bazom zahteva od DataSource objekta da otvori konekciju ka bazi
- DataSoruce mora da iskoristi drajver odgovarajuće baze da otvori konekciju ka istoj
- Konekcija se kreira i TCP socket se otvori
- Aplikacija u zavisnosti od zahteva klijenta upisuje ili čita podatke iz baze
- Konekcija ka bazi više nije potrebna te se zatvara
- TCP socket se zatvara

Connection pooling rešenja nam mogu pomoći da sagledamo razlike između slučajeva kada imamo implementiran i iskonfigurisan connection pool i kada isti nemamo.
Kad god se konekcija ka bazi zatraži, umesto da se svaki put kreira nova, dobaviće se slobodna konekcija iz connection pool-a. Connection pool će kreirati novu konekciju samo ako nema više slobodnih i pool nije dostigao svoju maksimalnu veličinu. Kada se obavi komunikacija sa bazom, konekcija se oslobađa i vraća u pool za ponovno korišćenje.
Developeri [HikariCP](https://github.com/brettwooldridge/HikariCP) kao jednog od trenutno najboljih rešenja za Java aplikacije istraživali su šta sve utiče na konfiguraciju connection pool-a i interesantan video možete videti na [linku](https://github.com/brettwooldridge/HikariCP/wiki/About-Pool-Sizing).

HikariCP se koristi kao podrazumevana implementacija connection poolinga u Spring Boot 2.x aplikacijama, te posebno uključivanje zavisnosti nije potrebno.
Dovoljno je samo u application.properties ili application.yml fajlu navesti konfiguracione parametre za pool.

Dodatni resursi:

- [[ISA] Resource pooling](https://www.youtube.com/watch?v=2OOkR4jgomU)
- [The anatomy of Connection Pooling](https://vladmihalcea.com/the-anatomy-of-connection-pooling/)
- [JDBC Connection Pooling](https://www.progress.com/tutorials/jdbc/jdbc-jdbc-connection-pooling)
- [JDBC Connection Pooling Best Practices](https://javaranch.com/journal/200601/JDBCConnectionPooling.html)
- [Tuning postgres connection pools](https://developer.bring.com/blog/tuning-postgres-connection-pools/)
- [Tomcat Thread Pool](https://tomcat.apache.org/tomcat-9.0-doc/config/executor.html)
- [How Exactly Does Tomcat's Thread Pool Work](https://stackoverflow.com/questions/22854498/how-exactly-does-tomcats-threadpool-work)
- [Configuring a Hikari Connection Pool with Spring Boot](https://www.baeldung.com/spring-boot-hikari)
- [Understanding HikariCP’s Connection Pooling behaviour](https://medium.com/@rajchandak1993/understanding-hikaricps-connection-pooling-behaviour-467c5a1a1506)

## API documentation

Danas je popularan odvojeni razvoj backend i frontend dela jedne web aplikacije, pre svega zbog sve kompleksnijih Javascript radnih okvira (Angular, React, Vue,...).
U takvom scenariju gde se backend API otkriva frontend delu aplikacije za korišćenje, potrebno je dobro projektovati API ali je isto tako važno, zbog lakše komunikacije između developera, taj API dokumentovati.
API dokumentacija mora biti čitljiva i laka za praćenje, ali to ne mora da znači da je za urednu dokumentaciju potrebno previše vremena, pogotovo ako se taj proces može automatizovati.
Za dokumentovanje API-ja moguće je koristiti [Swagger](https://swagger.io/) koji obezbeđuje jednostavan način za to. Na osnovu dokumentacije, Swagger omogućava i generisanje pristojne grafičke reprezentacije iste uz primitivni REST klijent za korišćenje dokumentovanog API-ja.
Swagger je iskorišćen u **cp-example** primeru.

Sve što je potrebno jesu tri stvari:
1. Uključiti zavisnosti za kreiranje dokumentacije i grafički prikaz.

```
        <dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.9.2</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.9.2</version>
		</dependency>
```
Ukoliko je uključena samo prva navedena zavisnost, dokumentacija se može videti u JSON formatu na adresi [http://localhost:8080/v2/api-docs](http://localhost:8080/v2/api-docs). Ako se uključi i druga zavisnost, dokumentacija se može videti u čitljivijem formatu na adresi [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

![Swagger UI](https://i.imgur.com/JI354gs.png "Swagger UI")

2. Registrovati Swagger kao Spring bean što je prikazano u klasi `SwaggerConfiguration.java`
3. Anotirati Spring kontrolere i metode odgovarajućim anotacijama opisanim na [linku](https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X)

## async-example

Asinhrono procesiranje je u Spring radnom okviru pojednostavljeno korišćenjem specijalnih anotacija.
Potrebno je uključiti podršku za asinhrono izvršavanje metoda pomoću anotacije `@EnableAsync` i anotirati metodu koja treba asinhrono da se izvršava pomoću anotacije `@Async`.
Kada se metoda anotira `@Async` anotacijom, Spring će izdvojiti izvršavanje te metode u odvojenu nit iz TaskExecutor thread pool-a, a pozivalac metode neće morati da čeka na njeno izvršavanje.

U **async-example** primeru prikazano je jednostavan kod za slanje e-maila sinhrono i asinhrono. Dodat je `Thread.sleep()` kako bi se istakao efekat dugotrajne operacije koja ima smisla da se izvršava asinhrono i kakav efekat takvo izvršavanje ima za korisnika. Za potrebe slanja e-maila koristi se objekat klase `JavaMailSender`. Konekcioni parametri za programsko slanje e-maila zadati su kroz `application.properties`.

Napomena: Za programsko slanje e-maila u primeru je korišćen Gmail nalog. Kako bi primer radio, potrebno je na nalogu koji ste postavili u `application.properties` dozvoliti rad sa "manje bezbednim aplikacijama". Na [linku](https://support.google.com/accounts/answer/6010255?hl=en) se nalazi uputstvo gde treba štiklirati __Off__ za "Less secure app access".

Dodatni materijali za razumevanje asinhronog izvršavanja metoda u Springu:

1. [Task Execution and Scheduling](https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling)
2. [Effective Advice on Spring Async: Part 1](https://dzone.com/articles/effective-advice-on-spring-async-part-1)
3. [Effective Advice on Spring Async (ExceptionHandler): Part 2](https://dzone.com/articles/effective-advice-on-spring-async-exceptionhandler-1)
4. [Effective Advice on Spring @Async: Final Part](https://dzone.com/articles/effective-advice-on-spring-async-final-part-1)
5. [How To Do @Async in Spring](https://www.baeldung.com/spring-async)


## Pokretanje Spring aplikacije (Eclipse)

* importovati projekat u workspace: Import -> Maven -> Existing Maven Project
* instalirati sve dependency-je iz pom.xml
* desni klik na projekat -> Run as -> Java Application / Spring Boot app (ako je instaliran STS plugin sa Eclipse marketplace)