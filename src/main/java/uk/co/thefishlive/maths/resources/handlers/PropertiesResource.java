package uk.co.thefishlive.maths.resources.handlers;

import uk.co.thefishlive.maths.resources.exception.ResourceException;

import java.io.IOException;
import java.net.URL;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertiesResource extends AbstractResource {
    public PropertiesResource(String path, URL url) {
        super(path, url);
    }

    public ResourceBundle getBundle() throws ResourceException {
        try {
            return new PropertyResourceBundle(getContent());
        } catch (IOException e) {
            throw new ResourceException("Could not load resource bundle", e);
        }
    }
}
