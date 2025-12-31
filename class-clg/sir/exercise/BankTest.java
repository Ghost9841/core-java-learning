public class BankTest {
    public static void main(String[] args) {
        BankAccount bank = new BankAccount();
        bank.setAccountHolderName("Aakash Subedi");
        bank.setAccountNumber("U9841");
        bank.setBalance(123456);
        bank.displayDetails();
    }
}
