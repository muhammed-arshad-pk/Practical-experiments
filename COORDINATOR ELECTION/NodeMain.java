import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NodeMain {

    static String[] ips = {
            "",
            "192.168.1.5",
            "192.168.1.6",
            "192.168.1.7"
    };

    static int coordinator = 3;

    public static void main(String[] args) {

        try {

            int nodeId = Integer.parseInt(args[0]);

            Node obj = new Node(nodeId);

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("Node" + nodeId, obj);

            System.out.println("Node " + nodeId + " ready");

            while (true) {

                if (nodeId != coordinator) {

                    try {

                        Registry reg = LocateRegistry.getRegistry(ips[coordinator],1099);

                        NodeInterface stub =
                                (NodeInterface) reg.lookup("Node" + coordinator);

                        stub.ping();

                        System.out.println("Ping sent to coordinator");

                    }

                    catch (Exception e) {

                        System.out.println("Coordinator failed!");

                        coordinator = nodeId;

                        System.out.println("Node " + nodeId + " becomes coordinator");

                        for (int i = 1; i <= 3; i++) {

                            if (i == nodeId) continue;

                            try {

                                Registry reg = LocateRegistry.getRegistry(ips[i],1099);

                                NodeInterface stub =
                                        (NodeInterface) reg.lookup("Node" + i);

                                stub.announceCoordinator(nodeId);

                            }

                            catch (Exception ex) {}

                        }
                    }
                }

                Thread.sleep(5000);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}