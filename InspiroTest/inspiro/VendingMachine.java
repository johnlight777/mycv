package inspiro;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {
    private Map<String, VendingMachineItem> vendingMachineItems = new HashMap();
    private Map<String, Item> mapItems = new HashMap();
    private List<Integer> acceptableMoney = Arrays.asList(2000, 5000, 10000, 20000, 50000);
    private Integer money = 0;

    public void initVendingMachineItems() {
        addStockToVendingMachine(new Item("B001", "Biscuit", 6000));
        addStockToVendingMachine(new Item("B001", "Biscuit", 6000));

        addStockToVendingMachine(new Item("C001", "Chips", 8000));
        addStockToVendingMachine(new Item("C001", "Chips", 8000));
        addStockToVendingMachine(new Item("C001", "Chips", 8000));

        addStockToVendingMachine(new Item("O001", "Oreo", 10000));
        addStockToVendingMachine(new Item("O001", "Oreo", 10000));
        addStockToVendingMachine(new Item("O001", "Oreo", 10000));
        addStockToVendingMachine(new Item("O001", "Oreo", 10000));

        addStockToVendingMachine(new Item("T001", "Tango", 12000));
        addStockToVendingMachine(new Item("T001", "Tango", 12000));
        addStockToVendingMachine(new Item("T001", "Tango", 12000));

        addStockToVendingMachine(new Item("C002", "Cokelat", 15000));
        addStockToVendingMachine(new Item("C002", "Cokelat", 15000));
        addStockToVendingMachine(new Item("C002", "Cokelat", 15000));

        System.out.println("Items Stocks : " + vendingMachineItems);
    }

    public void addStockToVendingMachine(Item item) {
        VendingMachineItem vendingMachineItem = vendingMachineItems.get(item.getCode());

        if (vendingMachineItem == null) {
            vendingMachineItem = new VendingMachineItem(item.getCode(), item.getPrice());
            vendingMachineItem.setStock(1);
            mapItems.put(item.getCode(), item);
            vendingMachineItems.put(item.getCode(), vendingMachineItem);
        } else {
            vendingMachineItem.addStock(1);
        }
    }

    public void insertMoney(Integer money) {
        moneyValidation(money);
        this.money += money;
    }

    public void processBuyItem(String itemCode) {
        Item item = mapItems.get(itemCode);
        moneyValidation(money);
        checkAvailabilityStock(item, money);
        checkPurchaseReturns(item, money);
        money = 0;

        System.out.println("Items Stocks after buy : " + vendingMachineItems);
    }

    private void checkAvailabilityStock(Item item, Integer money) {
        VendingMachineItem vendingMachineItem = vendingMachineItems.get(item.getCode());
        if (vendingMachineItem.getStock() < 1) returnMoney(money);
    }

    private void moneyValidation(Integer money) {
        if (!acceptableMoney.contains(money)) returnMoney(money);
    }

    private void checkPurchaseReturns(Item item, Integer money) {
        if (money > item.getPrice()) {
            System.out.println("purchase return : " + (money - item.getPrice()));
            returnMoney(money - item.getPrice());
        }
    }

    private Integer returnMoney(Integer money) {
        return money;
    }

}
