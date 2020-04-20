# Vežbe 5

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

## cache-example

U primeru je predstavljena ideja o keširanju kao konceptu i o postojanju dva nivoa keša koja Hibernate podržava - L1 i L2. Takođe, dati su i primeri kreiranja _NamedQuery_-ja i pisanja _query_-ja kao alternativnom načinu u odnosu na ono što je predstavljeno u __jpa-example__ primeru na vežbama 3.

U primeru je korišćena _in-memory_ baza [H2](http://www.h2database.com/html/main.html) koja je zgodna za brži i lakši razvoj i ne zahteva posebnu instalaciju (_workbench_-u se može pristupiti iz _browser_-a). Još neki proizvođači _in-memory_ baza su [HSQLDB](http://hsqldb.org/) i [Apache Derby](https://db.apache.org/derby/). H2 baza se integriše sa Maven aplikacijom dodavanjem sledeće zavisnosti:
```
<!-- Dependency za in-memory bazu H2 -->
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
</dependency>
```

### Level 1 keširanje - L1

Ovaj nivo keširanja je podržan od strane Hibernate-a, nije potrebna nikakva dodatna konfiguracija i ne može se isključiti. L1 keširanje se tiče Hibernate sesije. Kada se objekat učita u sesiju, prilikom svakog sledećeg upita za taj isti objekat, Hibernate neće slati upit ka bazi, već će objekat dobavljati iz keša. L1 keš omogućava da, unutar sesije, zahtev za objektom iz baze uvek vraća istu instancu objekta i tako sprečava konflikte u podacima i sprečava Hibernate da učita isti objekat više puta. Bitno je napomenuti da objekat koji se čuva u kešu na ovom nivu nije vidljiv drugim sesijama i "živi" koliko i Hibernate sesija. Kada se sesija uništi, uništava se i keš i svi objekti koji se u njemu nalaze.

Pošto Hibernate čuva sve objekte u L1 kešu, treba pažljivo i efikasno izvršavati _query_-je, da bi se izbegli potencijalni problemi sa memorijom. Na primer, ne treba čitati objekte iz baze ukoliko oni nisu potrebni, ne bi trebalo učitavati objekte u _for_ petlji, voditi računa o _fetch type_-u koji se koristi itd. Prilikom izrade projekta, postavite opciju `spring.jpa.show-sql = true` u `application.properties` fajlu i obratite pažnju kako izgledaju upiti koje Hibernate šalje bazi i koliko ih ima. Hibernate šalje mnogo upita i vrlo brzo keš postaje memorijski veoma zahtev. Problem sa veličinom L1 keša ne bi trebalo da imate (verovatno i nećete) prilikom izrade projektnog zadataka, ali to predstavlja realan problem o kojem treba voditi računa.

### Level 2 keširanje - L2

Ovaj nivo keširanja je podržan od Hibernate-a, ali je neophodan eksterni provajder. U primeru je korišćen [EhCache](http://www.ehcache.org/documentation/), ali postoje i drugi poput [Infinispan](https://infinispan.org/), [Redis](https://redis.io/) ili [JBoss Cache](https://jbosscache.jboss.org/).

Postoje različite strategije keširanja:

1. **Read Only:** strategija koju treba koristiti za objekte koji će se uvek čitati, ali se nikada neće ažurirati. Ova strategija je dobra kada se radi sa statičkim podacima, kao na primer konfiguracija aplikacije. Ovo je najjednostavnija strategija sa najboljim performansama, jer nema dodatnog posla da bi se proverilo da li je objekt ažuriran u bazi podataka ili nije
2. **Read Write:** strategija koju treba koristiti za objekte koji se mogu i ažurirati. Ukoliko se baza podataka ažurira i van aplikacije kroz neke druge, Hibernate neće biti svestan tih izmena, a podaci koji se čuvaju u kešu mogu biti zastareli. Zato treba voditi računa da kada se koristi ova strategija keširanja, baza podataka isključivo ažurira kroz Hibernate API
3. **Nonrestricted Read Write:** strategija koja se koristi ukoliko se podaci ažuriraju retko, skoro nikad. Ne garantuje konzistentnost između keša i baze podataka, i zbog toga je prihvatljiva u sistemima gde zastareli podaci ne predstavljaju kritične probleme
4. **Transactional:** strategija koja se koristi kod visoko konkurentnih sistema gde je ključno sprečiti da se u bazi podataka nalaze zastareli podaci.

#### EHCache

EhCache podržava sve navedene strategije keširanja, i zbog toga predstavlja jedan od najboljih i najpopularnijih provajdera za L2 keširanje u Spring aplikacijama. 

Da bi se EhCache uključio u Maven projekat, neophodno je uključiti sledeće zavisnosti:
```
<dependency>
	<groupId>org.ehcache</groupId>
	<artifactId>ehcache</artifactId>
</dependency>
<!-- Potrebno za logovanje dogadjaja -->
 <dependency>
	<groupId>javax.cache</groupId>
	<artifactId>cache-api</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

Konfiguracija EhCache-a zahteva definisanje XML fajla `ehcache.xml` koji se nalazi u `resources` folderu, kao i uključivanje podrške za keširanje dodavanjem anotacije `@EnableCaching` u neku od konfiguracionih klasa (u primeru, klasa _CacheExampleApplication_). Takođe, u `application.properties` potrebno je dodati liniju za učitavanje konfiguracije u aplikaciju:
```
spring.cache.jcache.config=classpath:ehcache.xml
```
Ostatak konfiguracije podrazumeva dodavanje anotacija nad klasama modela ili metodama koje vraćaju objekte koji se trebaju keširati, npr. `@Cacheable` ili `@CacheEvict` u klasi _ProductService_.

EhCache pruža mogućnost definisanja vremena koliko dugo objekat živi u kešu putem `<expiry>` elementa:

* ttl - TIME TO LIVE - ukupno vreme koje će objekti provesti u kešu bez obzira da li im se pristupa ili ne i
* tti - TIME TO IDLE - ukupno vreme koje će objekti provesti u kešu ako im se ne pristupa

Implementirana je i klasa `CacheLogger` u paketu _rs.ac.uns.ftn.informatika.cache.logger_ koja osluškuje svaku promenu u kešu i zadužena je za logovanje svih događaja. Događaji koji postoje su sledeći:

1. **CREATED** - dodavanje objekta u keš
2. **EXPIRED** - detekcija da je objektu isteklo vreme u kešu (ttl, tti)
3. **EVICTED** - izbacivanje objekta iz keša (dešava se ili eksplicitnim pozivanjem _evict_-a ili po principu **LRU** (*Least Recently Used*, kada se dodaje novi objekt u popunjen keš izbacuje se iz keša onaj koji se najmanje koristio)
4. **REMOVED** - uklanjanje objekta iz keša.

EhCache pruža mogućnost čuvanja keširanih objekata na Java heap-u, u RAM memoriji kao i na disku, što se podešava u `<resoruces>` elementu.

U primeru su konfigurisana dva keša:

1. **default**: predstavlja podrazumevani keš u kojem se keširaju svi objekti za koje nije naznačeno drugačije. Definiše se u `<cache-template name="default">` elementu.
2. **product**: keš kolekcija definisana se u `<cache alias="product" uses-template="default">` elementu. Atribut **alias** označava naziv keš kolekcije, dok **uses-template** atribut referencira šablon koji se _override_-uje. Vrednosti koje nisu navedene za keš kolekciju imaju vrednosti koje su definisane u referenciranom šablonu. Element `<key-type>` označava tip podatka koji će se koristiti za ključ, dok `<value-type>` označava tip podatka koji se nalaze u kešu. Prema konfiguraciji, u ovoj kolekciji se keširaju _Product_ objekti, a čuvaju se samo 2 objekta na Java _heap_-u. Kako će _resorces_ element da pregazi isti element iz _default_ keša, ne postoji mogućnost keširanja _Product_-a ni u RAM-u ni na disku.

U klasi `ProductService` iz paketa _rs.ac.uns.ftn.informatika.cache.service_ je definisana dodatna konfiguracija za EhCache:

1. Smeštanje _Product_ objekata u _product_ keš kolekciju

	```
	@Cacheable("product")
	Product findOne(long id);
	```

2. Eksplicitno brisanje svih objekata iz _product_ keš kolekcije

	```
	@CacheEvict(cacheNames = { "product" }, allEntries = true)
	void removeFromCache();
	```

### Demostracija primera

Preko Postman-a slati zahteve i u konzoli Spring aplikacije čitati ispise.

1. HTTP GET http://localhost:8080/products/1

	Ispis na konzoli:

	```
	2020-04-20 16:31:07.627  INFO 54359 --- [nio-8080-exec-1] r.a.u.f.i.c.service.ProductServiceImpl   : Product with id: 1 successfully cached!
	Hibernate: select product0_.id as id1_0_0_, product0_.name as name2_0_0_, product0_.origin as origin3_0_0_, product0_.price as price4_0_0_ from product product0_ where product0_.id=?
	2020-04-20 16:31:07.778  INFO 54359 --- [e [_default_]-0] r.a.u.f.i.cache.logger.CacheLogger       : Key: 1 | EventType: CREATED | Old value: null | New value: rs.ac.uns.ftn.informatika.cache.domain.Product@2d9a6dea
	```

	Desio se događaj **CREATED** i objekat je uspešno dodat u keš pod ključem 1. Vidimo da je objekat pročitan iz baze (deo loga koji počinje sa _Hibernate:_)

2. Pošto je u `ehcache.xml` fajlu definisano da je _ttl_ vreme 15 sekundi, sačekati više od 15 sekundi pre nego što se pošalje nov zahtev za preuzimanje ponovo istog objekta

3.  HTTP GET http://localhost:8080/products/1

	Ispis na konzoli:

	```
	2020-04-20 16:34:50.116  INFO 54359 --- [nio-8080-exec-5] r.a.u.f.i.c.service.ProductServiceImpl   : Product with id: 1 successfully cached!
	Hibernate: select product0_.id as id1_0_0_, product0_.name as name2_0_0_, product0_.origin as origin3_0_0_, product0_.price as price4_0_0_ from product product0_ where product0_.id=?
	2020-04-20 16:34:50.116  INFO 54359 --- [e [_default_]-1] r.a.u.f.i.cache.logger.CacheLogger       : Key: 1 | EventType: EXPIRED | Old value: rs.ac.uns.ftn.informatika.cache.domain.Product@2d9a6dea | New value: null
	2020-04-20 16:34:50.118  INFO 54359 --- [e [_default_]-1] r.a.u.f.i.cache.logger.CacheLogger       : Key: 1 | EventType: CREATED | Old value: null | New value: rs.ac.uns.ftn.informatika.cache.domain.Product@59fa46ae
	```

	Prvo se desio događaj **EXPIRED** za objekat koji je u keš dodat u koraku 1. Zatim se ponovo desio događaj **CREATED** i objekat je uspešno dodat u keš pod ključem 1. Vidimo da je objekat pročitan iz baze (deo loga koji počinje sa _Hibernate:_)

4. Za manje od 15 sekundi poslati novi zahtev (da ne istekne definisano _ttl_)
	
5.  HTTP GET http://localhost:8080/products/2

	Ispis na konzoli:

	```
	2020-04-20 16:38:07.904  INFO 54359 --- [nio-8080-exec-9] r.a.u.f.i.c.service.ProductServiceImpl   : Product with id: 2 successfully cached!
	Hibernate: select product0_.id as id1_0_0_, product0_.name as name2_0_0_, product0_.origin as origin3_0_0_, product0_.price as price4_0_0_ from product product0_ where product0_.id=?
	2020-04-20 16:38:07.907  INFO 54359 --- [e [_default_]-2] r.a.u.f.i.cache.logger.CacheLogger       : Key: 2 | EventType: CREATED | Old value: null | New value: rs.ac.uns.ftn.informatika.cache.domain.Product@91e5df7
	```

	Desio se događaj **CREATED** i objekat je uspešno dodat u keš pod ključem 2. Vidimo da je objekat pročitan iz baze (deo loga koji počinje sa _Hibernate:_)

6. Za manje od 15 sekundi poslati novi zahtev (da ne istekne definisano _ttl_). Dodaje se treći objekat u keš, a podešeno je da se u kešu čuvaju do dva objekta 

7.  HTTP GET http://localhost:8080/products/3

	Ispis na konzoli:

	```
	2020-04-20 16:41:56.692  INFO 54359 --- [nio-8080-exec-4] r.a.u.f.i.c.service.ProductServiceImpl   : Product with id: 3 successfully cached!
	Hibernate: select product0_.id as id1_0_0_, product0_.name as name2_0_0_, product0_.origin as origin3_0_0_, product0_.price as price4_0_0_ from product product0_ where product0_.id=?
	2020-04-20 16:41:56.694  INFO 54359 --- [e [_default_]-3] r.a.u.f.i.cache.logger.CacheLogger       : Key: 3 | EventType: CREATED | Old value: null | New value: rs.ac.uns.ftn.informatika.cache.domain.Product@39a3009d
	2020-04-20 16:41:56.696  INFO 54359 --- [e [_default_]-3] r.a.u.f.i.cache.logger.CacheLogger       : Key: 1 | EventType: EVICTED | Old value: rs.ac.uns.ftn.informatika.cache.domain.Product@7f9c0f4e | New value: null

	```

	Desio se događaj **CREATED** i objekat je uspešno dodat u keš pod ključem 3. Događaj **EVICTED** se dešava za objekat koji se u kešu čuva pod ključem 1, što znači da se po LRU principu, ovaj objekat izbacuje iz keša, a novokreirani objekat pod ključem 3 ubacuje u keš. Vidimo da je objekat pročitan iz baze (deo loga koji počinje sa _Hibernate:_)

8. Za manje od 15 sekundi poslati novi zahtev (da ne istekne definisano _ttl_)

9. HTTP GET http://localhost:8080/products/3

	Na konzoli nema nikakvog ispisa. Bitno je primetiti da ne postoji deo loga koji počinje sa _Hibernate:_, što znači da objekat koji je vraćen klijentu nije pročitan iz baze već iz keša.

### Alternativa pisanju _Query_-ja

Za malo kompleksnije upite ka bazi, metode repozitorijuma koje Hibernate koristi za gerenisanje upita, vrlo brzo postaju veoma kompleksne sa jako dugačkim nazivom i teško ih je napisati. Kao alternativni način pisanja, Hibernate nudi sledeće načine:

1. `@Query` anotacija koja se navodi iznad metode repozitorijuma proizvoljnog naziva. Atribut *value* predstavlja upit napisan [HQL](https://www.tutorialspoint.com/hibernate/hibernate_query_language.htm) sintaksom. Postoji podrška i za [Native SQL](https://www.tutorialspoint.com/hibernate/hibernate_native_sql.htm). Primer: `ProductQueryRepositoryExample` u _rs.ac.uns.ftn.informatika.cache.repository_ paketu
2. `@NamedQuery` anotacija koja se navodi iznad klase koje je definisana kao _@Entity_. Atribut **name** označava putanju do metode repozitorijuma, a atribut **query** predstavlja upit napisan HQL sintaksom koji se izvršava kada se metoda opisana u **name** atributu pozove. Primer: `ProductNamedQueryRepositoryExample` u _rs.ac.uns.ftn.informatika.cache.repository_ paketu i `Product` u _rs.ac.uns.ftn.informatika.cache.domain_ paketu.

### Dodatni materijali za razumevanje keširanja:

1. [Hibernate Second-Level Cache](https://www.baeldung.com/hibernate-second-level-cache)
2. [Difference between First and Second Level Cache in Hibernate](https://javarevisited.blogspot.com/2017/03/difference-between-first-and-second-level-cache-in-Hibernate.html)

## Pokretanje Spring aplikacije (Eclipse)

* importovati projekat u workspace: Import -> Maven -> Existing Maven Project
* instalirati sve dependency-je iz pom.xml
* desni klik na projekat -> Run as -> Java Application / Spring Boot app (ako je instaliran STS plugin sa Eclipse marketplace)