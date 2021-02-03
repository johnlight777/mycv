package inspiro;

public class Main {
    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.initVendingMachineItems();
        vendingMachine.insertMoney(5000);
        vendingMachine.insertMoney(5000);
        vendingMachine.insertMoney(5000);
        vendingMachine.processBuyItem("T001");
    }
}
