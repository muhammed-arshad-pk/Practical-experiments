import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;

public class InterestserviceImpl extends UnicastRemoteObject implements InterestService {

    protected InterestserviceImpl() throws RemoteException {
        super();
    }

    public double calculateSI(double p, double r, double t) throws RemoteException {

        double result = (p * r * t) / 100;

        try {
            String clientIP = RemoteServer.getClientHost();

            System.out.println("Client Connected : " + clientIP);
            System.out.println("Service Selected : Simple Interest");
            System.out.println("Principal : " + p);
            System.out.println("Rate : " + r);
            System.out.println("Time : " + t);
            System.out.println("Result : " + result);
            System.out.println("-----------------------------");

        } catch (Exception e) {
            System.out.println("Unable to get client IP");
        }

        return result;
    }

    public double calculateCI(double p, double r, double t) throws RemoteException {

        double result = p * Math.pow((1 + r / 100), t) - p;

        try {
            String clientIP = RemoteServer.getClientHost();

            System.out.println("Client Connected : " + clientIP);
            System.out.println("Service Selected : Compound Interest");
            System.out.println("Principal : " + p);
            System.out.println("Rate : " + r);
            System.out.println("Time : " + t);
            System.out.println("Result : " + result);
            System.out.println("-----------------------------");

        } catch (Exception e) {
            System.out.println("Unable to get client IP");
        }

        return result;
    }
}