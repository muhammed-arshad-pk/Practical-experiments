import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class InterestServer {

    public static void main(String[] args) {
        try {

            LocateRegistry.createRegistry(1099);

            InterestService service = new InterestserviceImpl();

            Naming.rebind("rmi://0.0.0.0/InterestService", service);

            System.out.println("RMI Server is running...");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}