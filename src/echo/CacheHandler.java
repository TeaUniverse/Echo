package echo;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.util.HashMap;

public class CacheHandler extends ProxyHandler {

    protected synchronized String response(String msg) throws InvalidKeyException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        SafeTable safeTable = SafeTable.getSafeTable(); // get the singleton cache
        if (safeTable.containsKey(msg)) {
            System.out.println(Thread.currentThread().getName() + ": Before being added to cache, response to message \"" + msg + "\" was found in the cache!");
            return safeTable.get(msg);
        }
        else {
            peer.send(msg);
            String res = peer.receive();
            if (res.equalsIgnoreCase("quit")) {
                super.shutDown();
            }
            System.out.println(Thread.currentThread().getName() + ": Before being added to cache, response to message \"" + msg + "\" was not found in the cache!");
            safeTable.put(msg, res);
            return res;
        }
    }

}
