package echo;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.util.HashMap;

public class SafeLog extends HashMap<String, String> {

    private static SafeLog safeLog = null;

    private SafeLog() { super(); };

    public static synchronized SafeLog getSafeLog() throws ClassCastException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (safeLog == null) {
            safeLog = new SafeLog();
        }
        return safeLog;
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
