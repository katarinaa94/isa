package rs.ac.uns.ftn.informatika.spring.boot.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.spring.boot.domain.Asset;

/*
 * Klasa je anotirana sa @Service sto treba da naznaci Springu da je klasa
 * Spring Bean i da treba da bude u nadleznosti Spring kontejnera.
 */
@Service
public class AssetServiceImpl implements AssetService {

	@PostConstruct
	public void initMetoda() throws Exception {
		System.out.println("Poziv init metode posle inicijalizacije komponente");
	}

	@PreDestroy
	public void cleanUpMetoda() throws Exception {
		System.out.println("Spring kontejner se gasi i komponenta se unistava!");
	}

	@Override
	public List<Asset> listAssets() {
		ArrayList<Asset> assets = new ArrayList<Asset>(2);
		assets.add(new Asset("Asset1", "Asset1 description"));
		assets.add(new Asset("Asset2", "Asset2 description"));
		return assets;
	}
}
