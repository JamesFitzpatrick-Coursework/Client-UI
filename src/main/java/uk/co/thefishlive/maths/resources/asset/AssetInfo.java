package uk.co.thefishlive.maths.resources.asset;

/**
 * Created by James on 11/12/2014.
 */
public class AssetInfo {

    private String path;
    private String hash;

    private AssetInfo() {}

    public AssetInfo(String path, String hash) {
        this.path = path;
        this.hash = hash;
    }

    public String getPath() {
        return this.path;
    }

    public String getHash() {
        return this.hash;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[path=" + this.getPath() + ";hash=" + this.getHash() + "]";
    }
}
