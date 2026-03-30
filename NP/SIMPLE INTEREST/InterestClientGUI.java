import javax.swing.*;
import java.awt.event.*;
import java.rmi.Naming;

public class InterestClientGUI extends JFrame implements ActionListener {

    JTextField ipField, principal, rate, time;
    JButton connectBtn, siBtn, ciBtn;
    JLabel result;

    InterestService service;

    InterestClientGUI() {

        setTitle("RMI Interest Calculator Client");
        setLayout(null);

        JLabel ipLabel = new JLabel("Server IP");
        JLabel l1 = new JLabel("Principal");
        JLabel l2 = new JLabel("Rate");
        JLabel l3 = new JLabel("Time");

        ipField = new JTextField();
        principal = new JTextField();
        rate = new JTextField();
        time = new JTextField();

        connectBtn = new JButton("Connect");
        siBtn = new JButton("Simple Interest");
        ciBtn = new JButton("Compound Interest");

        result = new JLabel("Result: ");

        ipLabel.setBounds(30,20,100,30);
        ipField.setBounds(120,20,150,30);
        connectBtn.setBounds(280,20,90,30);

        l1.setBounds(30,70,100,30);
        principal.setBounds(120,70,150,30);

        l2.setBounds(30,110,100,30);
        rate.setBounds(120,110,150,30);

        l3.setBounds(30,150,100,30);
        time.setBounds(120,150,150,30);

        siBtn.setBounds(30,200,150,30);
        ciBtn.setBounds(200,200,170,30);

        result.setBounds(30,250,350,30);

        add(ipLabel);
        add(ipField);
        add(connectBtn);

        add(l1);
        add(principal);

        add(l2);
        add(rate);

        add(l3);
        add(time);

        add(siBtn);
        add(ciBtn);
        add(result);

        connectBtn.addActionListener(this);
        siBtn.addActionListener(this);
        ciBtn.addActionListener(this);

        setSize(420,350);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {

        try {

            if(e.getSource() == connectBtn) {

                String ip = ipField.getText();
                service = (InterestService) Naming.lookup("rmi://" + ip + "/InterestService");

                result.setText("Connected to server " + ip);
            }

            double p = Double.parseDouble(principal.getText());
            double r = Double.parseDouble(rate.getText());
            double t = Double.parseDouble(time.getText());

            if(e.getSource() == siBtn) {

                double si = service.simpleInterest(p,r,t);
                result.setText("Simple Interest = " + si);
            }

            if(e.getSource() == ciBtn) {

                double ci = service.compoundInterest(p,r,t);
                result.setText("Compound Interest = " + ci);
            }

        } catch(Exception ex) {

            result.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {

        new InterestClientGUI();
    }
}