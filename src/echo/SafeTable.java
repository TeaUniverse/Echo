package echo;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.util.HashMap;

public class SafeTable extends HashMap<String, String> {

    private static SafeTable safeTable = null;

    private SafeTable() { super(); };

    public static synchronized SafeTable getSafeTable() throws ClassCastException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (safeTable == null) {
            safeTable = new SafeTable();
        }
        return safeTable;
    }

    public synchronized boolean containsKey(String request) {
        if (super.containsKey(request)) {
            return true;
        }
        return false;
    }

    public synchronized String get(String request) throws InvalidKeyException {
        return super.get(request);
    }

    public synchronized String put(String request, String response) {
        super.put(request, response);
        return request;
    }
}
