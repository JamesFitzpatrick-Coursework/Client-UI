package uk.co.thefishlive.maths.resources.asset;

import java.io.File;

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

    public String getLocalPath() {
        return hash.substring(0, 1) + File.separator + hash.substring(1, 2) + File.separator + hash;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[path=" + this.getPath() + ";hash=" + this.getHash() + "]";
    }
}
