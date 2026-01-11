public class BankAccountTest {
    public static void main(String[] args) {
        BankAccount b1 = new BankAccount();
        b1.setAccountHolderName("Aakash");
        b1.setAccountNumber("1234");
        b1.setBalance(123);
        b1.displayDetails();
    }
}
