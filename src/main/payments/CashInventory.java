package main.payments;

import java.util.HashMap;
import java.util.Map;

public class CashInventory {
    private Map<Payment, Integer> inventory;
    private Map<Payment, Integer> allInventories;


    public CashInventory() {
        inventory = new HashMap<>();
    }

    public CashInventory build(Map<Payment, Integer> inventory) {
        this.inventory = inventory;
        return this;
    }

    public void reset() {
        this.inventory = new HashMap<>();
        allInventories.forEach((key, value) -> inventory.put(key, value));
    }

    public int getQuantity(Payment item) {
        Integer value = allInventories.get(item);
        return value == null ? 0 : value;
    }

    public void deduct(Payment item, Integer numberOfPayments) {
        int count = allInventories.get(item);
        allInventories.put(item, count - numberOfPayments);
    }

    public void buildForChange(Map<Payment, Integer> insertedPayments) {
        allInventories = new HashMap<>();
        inventory.forEach((key, value) -> {
            if (allInventories.containsKey(key)) allInventories.put(key, value + allInventories.get(key));
            else allInventories.put(key, value);
        });
        insertedPayments.forEach((key, value) -> {
            if (allInventories.containsKey(key)) allInventories.put(key, value + allInventories.get(key));
            else allInventories.put(key, value);
        });
    }
}
