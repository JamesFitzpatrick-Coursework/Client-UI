package uk.co.thefishlive.maths.resources;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

public class ResourceReader extends Reader {

    private InputStreamReader reader;

    public ResourceReader(Resource resource) throws ResourceException {
        reader = new InputStreamReader(resource.getContent());
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return reader.read(cbuf, off, len);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
