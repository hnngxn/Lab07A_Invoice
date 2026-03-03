import javax.swing.*;
import java.awt.*;

public class InvoiceApp extends JFrame {

    private JTextField nameField, priceField, quantityField;
    private JTextArea displayArea;
    private Invoice invoice;

    public InvoiceApp() {
        invoice = new Invoice();
        setTitle("Invoice Application");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Line Item"));

        nameField     = new JTextField();
        priceField    = new JTextField();
        quantityField = new JTextField();

        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Unit Price:"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);

        JButton addBtn = new JButton("Add Item");
        JButton showBtn = new JButton("Show Invoice");
        inputPanel.add(addBtn);
        inputPanel.add(showBtn);

        // Display area
        displayArea = new JTextArea(12, 40);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Button actions
        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int qty = Integer.parseInt(quantityField.getText());

                Product p = new Product(name, price);
                LineItem li = new LineItem(p, qty);
                invoice.addLineItem(li);

                displayArea.append(String.format("Added: %-20s x%d @ $%.2f%n", name, qty, price));
                nameField.setText("");
                priceField.setText("");
                quantityField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for price and quantity.");
            }
        });

        showBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("========== INVOICE ==========\n");
            sb.append(String.format("%-20s %5s %10s %10s%n", "Product", "Qty", "Unit Price", "Total"));
            sb.append("-------------------------------------\n");
            for (LineItem li : invoice.getLineItems()) {
                sb.append(String.format("%-20s %5d %10.2f %10.2f%n",
                        li.getProduct().getName(),
                        li.getQuantity(),
                        li.getProduct().getUnitPrice(),
                        li.getTotal()));
            }
            sb.append("-------------------------------------\n");
            sb.append(String.format("%-20s %25.2f%n", "TOTAL DUE:", invoice.getTotalDue()));
            sb.append("==============================\n");
            displayArea.setText(sb.toString());
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new InvoiceApp();
    }
}