package uk.co.thefishlive.maths.resources.exception;

public class ResourceNotFoundException extends ResourceException {

    public ResourceNotFoundException(String s) {
        super("Could not find resource with path: " + s);
    }

}
