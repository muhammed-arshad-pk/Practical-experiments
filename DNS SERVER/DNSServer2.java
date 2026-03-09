import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;

public class DNSServer2 extends UnicastRemoteObject implements DNSService {

    HashMap<String, String> dnsTable;

    protected DNSServer2() throws RemoteException {

        dnsTable = new HashMap<>();

        dnsTable.put("google.com", "142.250.182.14");
        dnsTable.put("chatgpt.com", "104.18.12.123");
        dnsTable.put("youtube.com", "142.250.190.46");
        dnsTable.put("amazon.com", "205.251.242.103");
        dnsTable.put("facebook.com", "157.240.20.35");
    }

    public String resolveDomain(String domain) throws RemoteException {

        System.out.println("Server2 searching for: " + domain);

        if (dnsTable.containsKey(domain))
            return dnsTable.get(domain);

        return "NOT_FOUND";
    }

    public static void main(String[] args) {

        try {

            LocateRegistry.createRegistry(2000);

            DNSServer2 server = new DNSServer2();

            Naming.rebind("rmi://0.0.0.0:2000/DNS2", server);

            System.out.println("DNS Server 2 is running...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}