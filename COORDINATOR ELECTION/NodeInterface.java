import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NodeInterface extends Remote {

    void ping() throws RemoteException;

    void announceCoordinator(int id) throws RemoteException;

}