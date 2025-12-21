public class AccountTest {
    public static void main(String[] args) {
        Account account = new Account();
        account.setId(1122);
        account.setBalance(20000);
        account.setAnnualInterestRate(4.5);

        account.withdraw(2500);
        account.deposit(3000);

        System.out.printf("Balance: $%.2f%n", account.getBalance());
        System.out.printf("Monthly Interest Rate: %.2f%%%n", account.getMonthlyInterestRate());
    }
}
