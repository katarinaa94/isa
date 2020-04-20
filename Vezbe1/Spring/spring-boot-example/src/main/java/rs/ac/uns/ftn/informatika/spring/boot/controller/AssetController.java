package rs.ac.uns.ftn.informatika.spring.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import rs.ac.uns.ftn.informatika.spring.boot.domain.Asset;
import rs.ac.uns.ftn.informatika.spring.boot.service.AssetService;

/*
 * Klasa je anotirana sa @Controller sto treba da naznaci Springu da je klasa
 * Spring Bean i da treba da bude u nadleznosti Spring kontejnera.
 */
@Controller
public class AssetController {

	private AssetService assetService;

	/*
	 * setter based dependency injection
	 */
	@Autowired
	public void setAssetService(AssetService assetService) {
		this.assetService = assetService;
	}

	public List<Asset> getAssets() {
		return assetService.listAssets();
	}
}
