package echo;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.io.*;
import java.net.*;

/*
    Name: John Huynh
    Date: 04-28-2024
    Project Name: Echo
 */
public class Server {

    protected ServerSocket mySocket;
    protected int myPort;
    public static boolean DEBUG = true;
    protected Class<?> handlerType;

    public Server(int port, String handlerTypeName) {
        try {
            myPort = port;
            mySocket = new ServerSocket(myPort);
            this.handlerType = Class.forName(handlerTypeName);
        } catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } // catch
    }


    public void listen() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        while(true) {
            // accept a connection
            // make handler
            // start handler in its own thread
            try {
                Socket s = mySocket.accept();
                RequestHandler requestHandler = makeHandler(s);
                Thread thread = new Thread(requestHandler);
                thread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        } // while
    }

    public RequestHandler makeHandler(Socket s) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // handler = handlerType.getDeclaredConstructor().newInstance()
        // set handler's socket to s
        // return handler
        RequestHandler handler = (RequestHandler) handlerType.getDeclaredConstructor().newInstance();
        handler.setSocket(s);
        return handler;
    }



    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int port = 5555;
        String service = "echo.RequestHandler";
        if (1 <= args.length) {
            service = args[0];
        }
        if (2 <= args.length) {
            port = Integer.parseInt(args[1]);
        }
        Server server = new Server(port, service);
        System.out.println("Server listening at port " + port);
        server.listen();
    }
}