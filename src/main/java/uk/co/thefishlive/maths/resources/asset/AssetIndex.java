package uk.co.thefishlive.maths.resources.asset;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 *
 */
public class AssetIndex {

    private List<AssetInfo> assets;

    public AssetInfo getAsset(String path) {
        for (AssetInfo info : this.assets) {
            if (info.getPath().equalsIgnoreCase(path)) {
                return info;
            }
        }

        return null;
    }

    public List<AssetInfo> getAssets() {
        return ImmutableList.copyOf(assets);
    }
}
