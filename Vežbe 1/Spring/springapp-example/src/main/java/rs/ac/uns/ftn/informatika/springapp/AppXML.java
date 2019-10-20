package rs.ac.uns.ftn.informatika.springapp;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import rs.ac.uns.ftn.informatika.springapp.controller.AssetControllerConstructorDIXML;
import rs.ac.uns.ftn.informatika.springapp.controller.AssetControllerSetterDIXML;
import rs.ac.uns.ftn.informatika.springapp.domain.Asset;

public class AppXML {
	public static void main( String[] args ) {

		/*
		 * ClassPathXmlApplicationContext omogucava dobavljanje konteksta preko kojeg se
		 * mogu dobiti beanovi iz Spring kontejnera.
		 */
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		AssetControllerConstructorDIXML assetControllerConst = context.getBean(AssetControllerConstructorDIXML.class);
		AssetControllerSetterDIXML assetControllerSet = context.getBean(AssetControllerSetterDIXML.class);

		List<Asset> assetsConst = assetControllerConst.getAssets();
		List<Asset> assetsSet = assetControllerSet.getAssets();

		for(Asset asset : assetsConst){
			System.out.println(asset.getName());
			System.out.println(asset.getDescription());
		}

		for(Asset asset : assetsSet){
			System.out.println(asset.getName());
			System.out.println(asset.getDescription());
		}

		context.close();
	}

}
