package uk.co.thefishlive.maths;

import org.junit.Test;
import uk.co.thefishlive.meteor.utils.ProxyUtils;

import java.net.*;

/**
 * Created by James on 02/12/2014.
 */
public class ProxyTest {

    @Test
    public void testProxySettings() throws URISyntaxException {
        System.out.println("Detecting proxy");
        Proxy proxy = ProxyUtils.getSystemProxy();
        System.out.println("proxy type : " + proxy.type());

        InetSocketAddress address = (InetSocketAddress) proxy.address();

        if (address == null) {
            System.out.println("No Proxy");
        } else {
            System.out.println("proxy hostname : " + address.getHostName());
            System.out.println("proxy port : " + address.getPort());
        }
    }
}
