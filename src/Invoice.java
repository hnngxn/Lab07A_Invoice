import java.util.ArrayList;

public class Invoice {
    private ArrayList<LineItem> lineItems;

    public Invoice() {
        lineItems = new ArrayList<>();
    }

    public void addLineItem(LineItem item) {
        lineItems.add(item);
    }

    public ArrayList<LineItem> getLineItems() { return lineItems; }

    public double getTotalDue() {
        double total = 0;
        for (LineItem item : lineItems)
            total += item.getTotal();
        return total;
    }
}