# Vežbe 1 - Spring

## instaliranje dependency-ja iz pom.xml

Kada učitamo Maven projekat u workspace, sve biblioteke od kojih naš prjekat zavisi i koje su navedene u pom.xml fajlu se prvo traže u lokalnom Maven repozitorijumu (folder čiji je naziv _.m2_). Tek ukoliko se ne pronađu tu, biblioteke će se potražiti na remote Maven repozitorujumu i ubaciti u lokalni repozitorijum. Maven kada pronađe sve zavisnosti, uvezaće ih sa projektom. Nakon import-a projekta u workspace, u donjem desnom uglu status bara se nalazi progress učitavanja i build-ovanja Maven projekta. Ukoliko nakon build-a projekat i dalje ima greške, potrebno je ručno uvezati sve zavisnosti iz pom.xml sa projektom. Ovo može da se uradi na sledeći način:

* desni klik na projekat -> Run as -> Maven build... -> u polje _goals_ uneti _clean compile install_ -> Apply -> Run. Da bi ovo radilo, voditi računa da je za pokretanje Maven ciljeva postavljen JDK, a ne JRE. Ovo možete podesiti u tabu _JRE_ kada otvorite _Maven build..._ dijalog.
* desni klik na projekat -> Maven -> Update Project

Kada dodate novu zavisnost u pom.xml, na snimanje izmena će se automatski pokrenuti uvezivanje zavisnosti sa projektom. Ukoliko se to ne desi, pokrenuti ručno uvezivanje zavisnosti sa projektom.

## springapp-xml-configuration

Primer Spring aplikacije sa XML konfiguracijom.

Lokacija XML fajla odakle se čita konfiguracija: src/main/resources/applicationContext.xml

###### Pokretanje primera (Eclipse)

* importovati projekat u workspace: Import -> Maven -> Existing Maven Project
* instalirati sve dependency-je iz pom.xml
* desni klik na projekat -> Run as -> Java Application

## springapp-example

Primer Spring aplikacije sa automatskom konfiguracijom.

###### Pokretanje primera (Eclipse):

* importovati projekat u workspace: Import -> Maven -> Existing Maven Project
* instalirati sve dependency-je iz pom.xml
* desni klik na projekat -> Run as -> Java Application

## spring-boot-example

Primer Spring Boot aplikacije. Ovo je današnji preporučeni način za konfigurisanje Spring Boot aplikacija koji će se koristiti u svim narednim primerima.

###### Pokretanje primera (Eclipse):

* importovati projekat u workspace: Import -> Maven -> Existing Maven Project
* instalirati sve dependency-je iz pom.xml
* desni klik na projekat -> Run as -> Java Application / Spring Boot app (ako je instaliran STS plugin sa Eclipse marketplace)
