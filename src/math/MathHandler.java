package math;

import echo.RequestHandler;

import java.net.Socket;

public class MathHandler extends RequestHandler {

    public MathHandler(Socket sock) {
        super(sock);
    }

    public MathHandler() {
        super();
    }

    @Override
    protected String response(String request) throws Exception {
        //System.out.println("MathHandler: " + request);
        String[] res = request.split("\\s+");
        //System.out.println(res[0]);
        if (res.length < 3) {
            //throw new IllegalArgumentException("at least 2 inputs needed");
            return "at least 2 inputs needed";
        }
        if (res[0].equalsIgnoreCase("add")) {
            try {
                double sum = 0.0;
                for (int i=1;i<res.length;i++) {
                    sum += Double.parseDouble(res[i]);
                }
                return sum + "";
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
        else if (res[0].equalsIgnoreCase("mul")) {
            try {
                double mul = 1.0;
                for (int i = 1; i < res.length; i++) {
                    mul *= Double.parseDouble(res[i]);
                }
                return mul + "";
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
        else if (res[0].equalsIgnoreCase("sub")) {
            try {
                double sub = Double.parseDouble(res[1]);
                for (int i=2;i<res.length;i++) {
                    sub -= Double.parseDouble(res[i]);
                }
                sub -= 0;
                return sub + "";
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
        else if (res[0].equalsIgnoreCase("div")) {
            try {
                double div = Double.parseDouble(res[1]);
                for (int i=2;i<res.length;i++) {
                    if (res[i].equalsIgnoreCase("0")) {
                        throw new ArithmeticException("Error: Division by 0");
                    }
                    div /= Double.parseDouble(res[i]);
                }
                return div + "";
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
        return "Invalid operator";
    }
}
