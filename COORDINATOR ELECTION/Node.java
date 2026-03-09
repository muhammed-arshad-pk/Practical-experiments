import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Node extends UnicastRemoteObject implements NodeInterface {

    int nodeId;
    static int coordinator = 3;

    protected Node(int id) throws RemoteException {
        nodeId = id;
    }

    public void ping() throws RemoteException {
        System.out.println("Ping received at Node " + nodeId);
    }

    public void announceCoordinator(int id) throws RemoteException {
        coordinator = id;
        System.out.println("New Coordinator is Node " + coordinator);
    }
}