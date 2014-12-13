package uk.co.thefishlive.maths.resources.asset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;
import uk.co.thefishlive.maths.resources.CachingResourceManger;
import uk.co.thefishlive.maths.resources.Resource;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import uk.co.thefishlive.maths.resources.exception.ResourceNotFoundException;

public class AssetResourceManager extends CachingResourceManger {

    private static final Gson GSON = new GsonBuilder()
                                            .create();

    private Map<String, AssetInfo> assets;
    private File baseDir;

    public AssetResourceManager(File indexFile) throws ResourceException {
        try (FileReader reader = new FileReader(indexFile)) {
            AssetIndex index = GSON.fromJson(reader, AssetIndex.class);
            baseDir = new File(indexFile.getParentFile(), index.getBaseDir());

            for (AssetInfo info : index.getAssets()) {
                assets.put(info.getPath(), info);
            }
        } catch (IOException ex) {
            throw new ResourceException(ex);
        }
    }

    @Override
    public Resource loadResource(String path) throws ResourceException {
        AssetInfo info = assets.get(path);

        if (info == null) {
            throw new ResourceNotFoundException(path);
        }

        return new AssetResource(baseDir, info);
    }
}
