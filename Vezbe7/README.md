# Vežbe 7

## Message Queue

Za potrebe razumevanja primera potrebno je prethodno pročitati [MessageQueue.pdf](https://github.com/katarinaa94/isa/blob/master/Vezbe7/MessageQueue.pdf) iz foldera Vezbe6.

Kratak pregled prednosti korišćenja MQ nalazi se na [linku](https://blog.iron.io/top-10-uses-for-message-queue/).

Dodatne informacije o konceptima i različitim implementacijama MQ možete pročitati na [1](https://blog.codepath.com/2013/01/06/asynchronous-processing-in-web-applications-part-2-developers-need-to-understand-message-queues/) i [2](https://www.rabbitmq.com/tutorials/amqp-concepts.html).

Primer komunikacije zasnovane na razmeni poruka između dve Spring aplikacije i rada sa [RabbitMQ](https://www.rabbitmq.com/download.html) nalaze se u _rabbitmq-producer-example_ i _rabbitmq-consumer-example_ projektima. Za pokretanje primera potrebno je instalirati [RabbitMQ](https://www.rabbitmq.com/download.html). Kada se server instalira potrebno ga je startovati.

Podrška za korišćenje RabbitMQ u Spring aplikaciji se može uključiti dodavanjem odgovarajuće zavisnosti u `pom.xml`:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

### rabbitmq-producer-example

Primer Spring aplikacije koja dodaje poruke u red.

Pre svega, potrebno je uspostaviti konekciju sa MQ serverom. MQ server je zadužen za čuvanje pristiglih poruka. U _RabbitmqProducerExampleApplication_ klasi se u `connectionFactory()` metodi uspostavlja konekcija sa MQ serverom. U primeru koristimo lokalni RabbitMQ server, ali to može da bude i neki cloud server, poput [CloudaMQP](https://www.cloudamqp.com/). U ovoj klasi se definišu i dva reda

```
@Bean
Queue queue(){
    return new Queue(queue, false);
}
``` 
i

```
@Bean
Queue queue2(){
    return new Queue(queue2, false);
}
```
gde je drugi parametar konstruktora vrednost `durable` atributa. Nazivi atributa su definisani u `application.properties` fajlu, gde se uz pomoć `@Value` anotacije injektuju u String varijable.

Definisan je jedan `binding rule`:

```
@Bean
DirectExchange exchange() {
    return new DirectExchange(exchange);
}

@Bean
Binding binding(Queue queue2, DirectExchange exchange) {
    return BindingBuilder.bind(queue2).to(exchange).with(routingkey);
}
```

Ovim se `queue2` vezuje za _exchange_ (parametar _to()_ metode je naziv) pod definisanim ključem (parametar _with()_ metode je ključ, odnosno _routing key_). Definisan je _Direct Exchange_, a podržani tipovi su predstavljeni klasom [Exchange Type](https://docs.spring.io/spring-amqp/api/org/springframework/amqp/core/ExchangeTypes.html). Kako za `queue` nije definisan _exchange_, on se vezuje za _Default Exchange_, gde je njegov naziv zapravo i _Routing Key_.

U klasi _Producer_ su implementirane dve metode:

1. `sendTo()` metoda koja šalje poruku na _Default Exchange_. Parametri metode su _routing key_ i poruka koja se šalje
2. `sendToExchange()` metoda koja šalje poruku na _Exchange_. Parametri metode su _exchange_, _routing key_ i poruka koja se šalje.  

Sve poruke se šalju preko [RabbitTemplate](https://docs.spring.io/spring-amqp/docs/current/api/org/springframework/amqp/rabbit/core/RabbitTemplate.html) preko kojeg se ostvaruje komunikacija sa RabbitMQ serverom i pruža mogućnost rutiranja, slanja i primanja poruka.

Klasa _ProducerController_ je REST _controller_ sa dva _endpoint_-a:

1. _endpoint_ na koji se šalju poruke koje se dalje prosleđuju na _Default Exchange_ i
2. _endpoint_ na koji se šalju poruke koje se dalje prosleđuju na određeni _Exchange_.

Ovo znači da imamo jednu Spring aplikaciju koja koristi dva različita načina komunikacije: REST i Message Queue!

### rabbitmq-consumer-example

Primer Spring aplikacije koja čita poruke iz reda.

Pre svega, potrebno je uspostaviti konekciju sa MQ serverom. MQ server je zadužen za čuvanje pristiglih poruka. U _RabbitmqConsumerExampleApplication_ klasi se u `connectionFactory()` metodi uspostavlja konekcija sa MQ serverom. U primeru koristimo lokalni RabbitMQ server, ali to može da bude i neki cloud server, poput [CloudaMQP](https://www.cloudamqp.com/).

Definisana su dva _Consumer_-a koji čitaju poruke koji su predstavljeni u dve različite klase koje imaju jednu metodu `public void handler(String message)` koja je anotirana `@RabbitListener` anotacijom. Ova anotacija ima jedan parametar `queues=` čija vrednost označava nazive redova sa kojih _Consumer_ čita poruke. U primeru, prvi _Consumer_ čita poruke sa _spring-boot1_ reda, a drugi sa _spring-boot2_ reda.

Listener će konvertovati poruku u odgovarajući tip koristeći odgovarajući konvertor poruka (implementacija [MessageConverter interfejsa](https://docs.spring.io/spring-amqp/api/org/springframework/amqp/support/converter/MessageConverter.html)).

### Pokretanje primera

Da bi se primer uspešno demonstrirao, neophodno je da _producer_ i _consumer_ budu povezani na isti RabbitMQ server, jer se svi redovi i poruke čuvaju na jednom serveru.

1. Pokrenuti _rabbitmq-producer-example_ (radi na portu 8080)
2. Preko _Postman_-a poslati poruku na _Default Exchange_, red _spring-boot1_ (slika 1)
3. Pogledati ispis u konzoli: `Sending> ... Message=[ hello! ] RoutingKey=[spring-boot1]`
4. Pokrenuti _rabbitmq-consumer-example_ (radi na portu 8081)
5. Odmah nakon pokretanja, u konzoli se ispisuje `Consumer> hello!` zato što prilikom startovanja aplikacije, _consumer_ se automatski pretplati na red na koji je poslata poruka u koraku 3, a kako ta poruka nije obrađena, odmah je čita i obrađuje
6. Preko _Postman_-a poslati još jednu poruku na _Exchange_ pod nazivom _myexchange_, red _spring-boot2_ (slika 2)
7. Pogledati ispis u konzoli _rabbitmq-producer-example_ aplikacije: `Sending> ... Message=[ hello hello ] Exchange=[myexchange] RoutingKey=[spring-boot2]`
8. Pogledati ispis u konzoli _rabbitmq-consumer-example_ aplikacije: `Consumer2> hello hello`
9. Preko _Postman_-a poslati još jednu poruku na _Exchange_ pod nazivom _myexchange_, ali na red _spring-boot1_ koji je vezan za _Default Exchange_ (slika 3)
10. Pogledati ispis u konzoli _rabbitmq-producer-example_ aplikacije: `Sending> ... Message=[ hello hello ] Exchange=[myexchange] RoutingKey=[spring-boot1]`
11. Na konzoli _rabbitmq-consumer-example_ aplikacije nema ispisa da je neki _consumer_ obradio poruku jer za _myexchange_ ne postoji _routing key_ sa vrednošću _spring-boot1_

Takođe, možete da pristupite lokalnoj konzoli RabbitMQ servera tako što ćete u _Browser_-u ukucati http://localhost:15672/#/, kredencijali su _username: guest, password: guest_. Ovde možete da vidite sve redove koji su definisani, da pratite razmenu poruka, šaljete poruke...

Napomena: važno je da se ili ručno kreira red preko konzole ili da se pošalje bar jedna poruka na bilo koji red pre nego što se pokrene _rabbitmq-consumer-example_ jer red mora da postoji na serveru da bi se moglo na njega pretplatiti putem `@RabbitListener` anotacije!

![Slika 1](https://i.imgur.com/mxMCxZo.png "Slika 1")
Slika 1

![Slika 2](https://i.imgur.com/XgleBua.pngj "Slika 2")
Slika 2

![Slika 3](https://i.imgur.com/NwqtPD1.pngj "Slika 3")
Slika 3
