# Vežbe 1 - Tools

Ovo su primeri informativnog karaktera.

## ant-example

Primer Apache Ant aplikacije.

###### Pokretanje primera (Eclipse)

* importovati projekat u workspace: Import -> General -> Existing Projects into Workspace
* nad odgovarajućim ciljem iz build.xml pokrenuti "Ant Build". Cilj odaberete ili iz Outline prozorčića ili desni klik na _target_ tag u otvorenom dokumentu -> Run as -> Ant Build.
* ukoliko želite da pokrenete više od jednog cilja, potrebno je otvoriti Ant Run konfiguraciju (Run as -> Ant build...) -> označiti sve ciljeve koji treba da se izvrše -> Apply -> Run.

## ivy-example

Primer Apache Ivy aplikacije.

###### Pokretanje primera (Eclipse)

* importovati projekat u workspace: Import -> General -> Existing Projects into Workspace
* instalirati Ivy plugin
* desni klik na ivy.xml -> Add Ivy Library...
* desni klik na projekat -> Run as -> Java Application

## maven-example

Primer Maven aplikacije.

###### Pokretanje primera (Eclipse)

* importovati projekat u workspace: Import -> Maven -> Existing Maven Project
* instalirati sve dependency-je iz pom.xml
* desni klik na projekat -> Run as -> Java Application
