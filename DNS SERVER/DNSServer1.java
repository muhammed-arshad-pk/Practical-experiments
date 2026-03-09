import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;

public class DNSServer1 extends UnicastRemoteObject implements DNSService {

    HashMap<String, String> dnsTable;

    protected DNSServer1() throws RemoteException {

        dnsTable = new HashMap<>();

        dnsTable.put("linkedin.com", "108.174.10.10");
        dnsTable.put("github.com", "140.82.113.4");
        dnsTable.put("stackoverflow.com", "151.101.65.69");
    }

    public String resolveDomain(String domain) throws RemoteException {

        System.out.println("Client requested: " + domain);

        if (dnsTable.containsKey(domain)) {

            System.out.println("Found in Server1");

            return dnsTable.get(domain);
        }

        try {

            System.out.println("Not found in Server1. Querying Server2...");

            DNSService dns2 = (DNSService) Naming.lookup("rmi://SERVER2_IP:2000/DNS2");

            String result = dns2.resolveDomain(domain);

            return result;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "NOT_FOUND";
    }

    public static void main(String[] args) {

        try {

            LocateRegistry.createRegistry(1099);

            DNSServer1 server = new DNSServer1();

            Naming.rebind("rmi://0.0.0.0:1099/DNS1", server);

            System.out.println("DNS Server 1 is running...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}