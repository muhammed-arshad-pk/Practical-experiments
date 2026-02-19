import java.rmi.registry.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            // Hard-coded Server IP
            String serverIP = "10.10.166.253";

            Registry registry =
                    LocateRegistry.getRegistry(serverIP, 1099);

            Calculator calc =
                    (Calculator) registry.lookup("CalcService");

            Scanner sc = new Scanner(System.in);
            System.out.println("Connected to RMI Server!");

            while (true) {
                System.out.println("\n1.Add 2.Sub 3.Mul 4.Div 5.Exit");
                int ch = sc.nextInt();

                if (ch == 5) break;

                double a = sc.nextDouble();
                double b = sc.nextDouble();

                double result = switch (ch) {
                    case 1 -> calc.add(a, b);
                    case 2 -> calc.sub(a, b);
                    case 3 -> calc.mul(a, b);
                    case 4 -> calc.div(a, b);
                    default -> 0;
                };

                System.out.println("Result = " + result);
            }
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
