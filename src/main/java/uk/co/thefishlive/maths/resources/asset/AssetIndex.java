package uk.co.thefishlive.maths.resources.asset;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 *
 */
public class AssetIndex {

    private String id;
    private List<AssetInfo> assets;
    private String basedir;

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

    public String getId() {
        return this.id;
    }

    public String getBaseDir() {
        return basedir;
    }
}
