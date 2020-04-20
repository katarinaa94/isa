package rs.ac.uns.ftn.informatika.spring.boot;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import rs.ac.uns.ftn.informatika.spring.boot.controller.AssetController;
import rs.ac.uns.ftn.informatika.spring.boot.domain.Asset;

/*
* @SpringBootApplication anotacija obuhvata 
* 	1. @Configuration, 
* 	2. @EnableAutoConfiguration i 
* 	3. @ComponentScan anotacije
* sa njihovim default-nim atributima.
* 
* @EnableAutoConfiguration anotacija upravlja konfiguracijom aplikacije. Sadrzi 'auto-configuration feature' na osnovu kojeg 
* Spring Boot gledajuci classpath (pom.xml), anotacije i konfiguraciju dodaje potrebne tehnologije i kreira aplikaciju.
* 
* Iako koristimo @SpringBootApplication anotaciju, mogu se eksplicitno navesti sve navedene anotacije da bismo promenili njihove default-ne vrednost
* npr. @ComponentScan("rs.ac.uns.ftn.informatika.spring.boot")
* 
*/
@SpringBootApplication
public class SpringBootExampleApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpringBootExampleApplication.class, args);

		AssetController controller = (AssetController) ctx.getBean("assetController");
		List<Asset> assets = controller.getAssets();

		for (Asset asset : assets) {
			System.out.println(asset.getName());
			System.out.println(asset.getDescription());
		}
	}
}
