import javax.swing.*;
import java.awt.event.*;
import java.rmi.Naming;

public class DNSClientGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Simple Browser");

        JTextField domainField = new JTextField(20);

        JButton search = new JButton("Search");

        JTextArea output = new JTextArea(10,30);

        JScrollPane scroll = new JScrollPane(output);

        JPanel panel = new JPanel();

        panel.add(new JLabel("Enter Domain: "));
        panel.add(domainField);
        panel.add(search);

        frame.add(panel,"North");
        frame.add(scroll,"Center");

        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        search.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    String domain = domainField.getText();

                    output.append("Searching domain: " + domain + "\n");

                    output.append("Contacting DNS Server 1...\n");

                    DNSService dns = (DNSService) Naming.lookup("rmi://SERVER1_IP:1099/DNS1");

                    String ip = dns.resolveDomain(domain);

                    if(!ip.equals("NOT_FOUND")){

                        output.append("IP Found: " + ip + "\n");

                        output.append("Opening webpage at " + ip + "\n\n");

                    } else {

                        output.append("Domain not found in DNS system\n\n");
                    }

                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }
        });
    }
}