package rs.ac.uns.ftn.informatika.springapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
/*
 * @ComponentScan anotacija naznacava koji ce paketi proci kroz proces
 * skeniranja u cilju pronalazenja anotacija koje definisu komponente ili
 * beanove kojima Spring kontejner treba da upravlja.
 * 
 * Default-na vrednost jeste direktorijum gde se nalazai main klasa.
 * 
 * Svi bean-ovi koji ce biti automatski prepoznati od strane Spring kontejnera
 * moraju da se nalaze u podpaketima source direktorijuma, na bilo kojoj dubini.
 */
@ComponentScan(value = { "rs.ac.uns.ftn.informatika.springapp" })
public class AppConfiguration {

	/*
	 * Alternativno se komponente mogu registrovati kao beanovi unutar jedne ili
	 * vise konfiguracionih klasa (klase su anotirane sa @Configuration)
	 */
	/*
	 * @Bean public AssetService getAssetService() { return new AssetServiceImpl();
	 * }
	 */

}
