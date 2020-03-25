package rs.ac.uns.ftn.informatika.springapp.service;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.informatika.springapp.domain.Asset;

public class AssetServiceImpl implements AssetService {

    public List<Asset> listAssets() {
        ArrayList<Asset> assets = new ArrayList<Asset>();
        assets.add(new Asset("Asset1", "Asset1 description"));
        assets.add(new Asset("Asset2", "Asset2 description"));
        return assets;
    }
}