package echo;

import java.net.Socket;

import static java.lang.Thread.sleep;

public class RequestHandler extends Correspondent implements Runnable {
    protected boolean active; // response can set to false to terminate thread
    public RequestHandler(Socket s) {
        super(s);
        active = true;
    }
    public RequestHandler() {
        super();
        active = true;
    }
    // override in a subclass:
    protected String response(String msg) throws Exception {
        return "echo: " + msg;
    }
    // any housekeeping can be done by an override of this:
    protected void shutDown() {
        if (Server.DEBUG) System.out.println("handler shutting down");
    }
    public void run() {
        while(active) {
            try {
                // receive request
                String request = sockIn.readLine();
                if (request == null) continue;
                if(request.equals("quit")) {
                    shutDown();
                    break;
                }
                // send response
                //System.out.println("Request: " + request);
                String res = response(request);
                //if (Server.DEBUG) System.out.println("sending: " + res);

                System.out.println("received: " + request);
                send(res);
                System.out.println("sending: " + res);
                // sleep
                Thread.sleep(1000);
            } catch(Exception e) {
                send(e.getMessage() + "... ending session");
                break;
            }
        }
        // close
    }
}