import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.HashSet;
import java.util.Set;

public class Server {

    // Implementation class
    public static class CalculatorImpl extends UnicastRemoteObject
            implements Calculator {

        // To avoid printing same client again and again
        private static Set<String> connectedClients = new HashSet<>();

        protected CalculatorImpl() throws RemoteException {
            super();
        }

        // Method to log client details
        private void logClient() {
            try {
                String clientHost = RemoteServer.getClientHost();
                if (!connectedClients.contains(clientHost)) {
                    connectedClients.add(clientHost);
                    System.out.println(
                        "Client connected from IP: " + clientHost
                    );
                }
            } catch (ServerNotActiveException e) {
                System.out.println("Unable to fetch client details");
            }
        }

        public double add(double a, double b) {
            logClient();
            return a + b;
        }

        public double sub(double a, double b) {
            logClient();
            return a - b;
        }

        public double mul(double a, double b) {
            logClient();
            return a * b;
        }

        public double div(double a, double b) {
            logClient();
            if (b == 0) {
                System.out.println("Division by zero attempted!");
                return 0;
            }
            return a / b;
        }
    }

    // Server main
    public static void main(String[] args) {
        try {
            String serverIP = java.net.InetAddress
                    .getLocalHost()
                    .getHostAddress();

            System.setProperty("java.rmi.server.hostname", serverIP);

            CalculatorImpl obj = new CalculatorImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("CalcService", obj);

            System.out.println(
                "Calculator RMI Server started at " +
                serverIP + ":1099"
            );
            System.out.println("Waiting for clients...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
