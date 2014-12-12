package uk.co.thefishlive.maths.resources.asset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uk.co.thefishlive.maths.resources.Resource;
import uk.co.thefishlive.maths.resources.ResourceManager;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by James on 11/12/2014.
 */
public class AssetResourceManager implements ResourceManager {

    private static final Gson GSON = new GsonBuilder()
                                            .create();

    private AssetIndex index;

    public AssetResourceManager(File index) throws ResourceException {
        try (FileReader reader = new FileReader(index)) {
            this.index = GSON.fromJson(reader, AssetIndex.class);
        } catch (IOException ex) {
            throw new ResourceException(ex);
        }
    }

    @Override
    public Resource getResource(String path) throws ResourceException {
        return null;
    }
}
