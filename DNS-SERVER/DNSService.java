import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DNSService extends Remote {

    String resolveDomain(String domain) throws RemoteException;

}