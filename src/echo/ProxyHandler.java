package echo;

import java.net.Socket;

public class ProxyHandler extends RequestHandler {

    protected Correspondent peer;

    public ProxyHandler(Socket s) { super(s); }
    public ProxyHandler() { super(); }

    public void initPeer(String host, int port) {
        peer = new Correspondent();
        peer.requestConnection(host, port);
    }

    @Override
    protected void shutDown() {
        super.shutDown();

    }

    protected String response(String msg) throws Exception {
        // forward msg to peer
        // return peer's response
        peer.send(msg);
        String res = peer.receive();
        System.out.println("Peer executes command");
        if (res.equalsIgnoreCase("quit")) {
            super.shutDown();
        }
        return res;
    }
}