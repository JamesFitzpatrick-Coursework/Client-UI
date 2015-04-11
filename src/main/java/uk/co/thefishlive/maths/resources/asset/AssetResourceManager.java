package uk.co.thefishlive.maths.resources.asset;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.MalformedURLException;
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

    private Map<String, AssetInfo> assets = Maps.newHashMap();
    private File baseDir;

    public AssetResourceManager(File indexFile, File baseDir) throws ResourceException {
        try (FileReader reader = new FileReader(indexFile)) {
            // Load index file
            AssetIndex index = GSON.fromJson(reader, AssetIndex.class);
            this.baseDir = baseDir;

            // Load all assets from index
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

        // Cannot find asset required
        if (info == null) {
            throw new ResourceNotFoundException(path);
        }

        try {
            // Create resource from file
            File file = new File(baseDir, info.getLocalPath());
            return typeRegistry.createResource(path, file.toURI().toURL());
        } catch (MalformedURLException e) {
            throw new ResourceException(e);
        }
    }
}
