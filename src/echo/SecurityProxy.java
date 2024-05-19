package echo;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;

public class SecurityProxy extends ProxyHandler {
//public class SecurityProxy extends CacheHandler {
    SafeTable users;
    Boolean loggedIn = false;
    SafeLog log; // log of currently logged in accounts as keys and thread id as value; empty string if no thread.
    String curAccount = ""; // current logged in account/username
    protected synchronized String response(String request) throws Exception {
        String[] cmmd = request.split("\\s+");
        String answer = "";
        users = SafeTable.getSafeTable(); // get singleton user table
        log = SafeLog.getSafeLog();
        System.out.println(Thread.currentThread().getName());

        if(cmmd[0].equalsIgnoreCase("new")) {
            if (cmmd.length != 3) {
                answer = "INVALID ACCOUNT CREATION. REQUIRE USERNAME AND PASSWORD";
                shutDown();
            }
            else if (users.get(cmmd[1]) == null ) {
                users.put(cmmd[1], cmmd[2]);
                answer = "ACCOUNT CREATED";
                shutDown();
            }
            else {
                answer = "ACCOUNT EXISTS";
                shutDown();
            }
        }
        else if (cmmd[0].equalsIgnoreCase("login")) {
            if (cmmd.length != 3) {
                answer = "INVALID LOGIN. REQUIRE USERNAME AND PASSWORD";
                shutDown();
            }
            else if (users.containsKey(cmmd[1]) && users.get(cmmd[1]).equals(cmmd[2])) {
                String currentThread = Thread.currentThread().getName();
                if (loggedIn) {
                    String username = users.get(cmmd[1]);
                    if (log.containsKey(username)) {
                        if (log.get(username).equalsIgnoreCase(currentThread)) {
                            answer = "YOU ARE CURRENTLY LOGGING IN THIS ACCOUNT";
                        }
                        else if (log.get(username).equalsIgnoreCase("")) {
                            answer = "LOG OUT OF CURRENT ACCOUNT BY TYPING \"LOGOUT\" BEFORE SWITCHING TO ANOTHER ACCOUNT.";
                        }
                        else {
                            answer = "ACCESS IS DENIED BECAUSE ANOTHER USER IS LOGGING IN THIS ACCOUNT.";
                        }
                    }
                    else {
                        answer = "LOG OUT OF CURRENT ACCOUNT BY TYPING \"LOGOUT\" BEFORE SWITCHING TO ANOTHER ACCOUNT.";
                    }
                    shutDown();
                }
                else {
                    String username = users.get(cmmd[1]);
                    if (log.containsKey(username) &&
                            !log.get(username).equalsIgnoreCase("")) {
                        answer = "ACCESS IS DENIED BECAUSE ANOTHER USER IS LOGGING IN THIS ACCOUNT.";
                        shutDown();
                    }
                    else {
                        loggedIn = true;
                        answer = "SUCCESSFULLY LOGGED IN.";
                        curAccount = users.get(cmmd[1]);
                        log.put(users.get(cmmd[1]), currentThread);
                    }
                }
            }
            else {
                System.out.println("Login Status: " + loggedIn);
                answer = "LOGIN FAILS.";
                shutDown();
            }
        }
        else if (cmmd[0].equalsIgnoreCase("logout")) {
            if (loggedIn) {
                loggedIn = false;
                log.put(curAccount, ""); // make account available to login
                curAccount = "";
                answer = "SUCCESSFULLY LOGGED OUT.";
                //shutDown();
            }
            else {
                answer = "LOGOUT FAILS. YOU ARE NOT CURRENTLY LOGGING IN.";
            }
        }
        else {
            if (loggedIn) {
                answer = super.response(request);
                //System.out.println("Already logged in");
            } else {
                // something bad
                answer = "LOGIN FAILS";
                shutDown();
            }
        }
        return answer;
    }
}
