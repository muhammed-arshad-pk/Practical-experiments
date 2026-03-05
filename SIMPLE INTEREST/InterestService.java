import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterestService extends Remote {

    double calculateSI(double p, double r, double t) throws RemoteException;

    double calculateCI(double p, double r, double t) throws RemoteException;

}