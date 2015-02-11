package uk.co.thefishlive.maths.resources.handlers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URL;
import uk.co.thefishlive.maths.resources.ResourceReader;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

public class JsonResource extends AbstractResource {
    public JsonResource(String path, URL url) {
        super(path, url);
    }

    public JsonObject parse() throws ResourceException {
        JsonParser parser = new JsonParser();
        return parser.parse(new ResourceReader(this)).getAsJsonObject();
    }
}
