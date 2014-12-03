package uk.co.thefishlive.maths.utils;

import java.net.*;
import java.util.List;

/**
 * Created by James on 03/12/2014.
 */
public class ProxyUtils {

    public static Proxy getSystemProxy() throws URISyntaxException {
        System.setProperty("java.net.useSystemProxies", "true");
        List<Proxy> l =  ProxySelector.getDefault().select(new URI("http://thefishlive.co.uk"));
        return l.get(0);
    }
}
