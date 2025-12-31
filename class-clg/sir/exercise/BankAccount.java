public class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    void setBalance(double balance) {
        if (balance > 0) {

            this.balance = balance;
        } else {
            System.out.println("Balance cant be negative");
        }
    }

    String getAccountHolderName() {
        return accountHolderName;
    }

    String getAccountNumber() {
        return accountNumber;
    }

    double getBalance() {
        return balance;
    }

    void displayDetails() {
        System.out.println("Account Holder: " + accountHolderName + "Account Balance: " + balance);
    }
}
